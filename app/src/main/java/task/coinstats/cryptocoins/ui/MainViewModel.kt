package task.coinstats.cryptocoins.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import task.coinstats.cryptocoins.data.models.local.Coin
import task.coinstats.cryptocoins.domain.CoinsInteractor
import task.coinstats.cryptocoins.utils.Result
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val interactor: CoinsInteractor
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.UNINITIALIZED)
    val uiState: StateFlow<UiState> get() = _uiState

    private var _coins = listOf<Coin>()

    private val updatePeriod: Long = 5000L

    private var updateJob: Job? = null

    init {
        getInitialData()
    }

    fun onRefresh() {
        updateJob?.cancel()
        getInitialData()
    }

    fun getData() {
        if (_coins.isEmpty())
            getInitialData()
        else {
            _uiState.update { UiState.Coins(_coins) }
        }
    }

    private fun getInitialData() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { UiState.LOADING }
            when (val result = interactor.getCoins()) {
                is Result.Error<*> -> {
                    result.errors.errorMessage?.let { it1 ->
                        _uiState.update { UiState.Error(it1) }
                    }
                }
                is Result.Success -> {
                    if (!result.data.isNullOrEmpty()) {
                        _coins = result.data
                        _uiState.update { UiState.Coins(_coins) }
                        startUpdates()
                    }
                }
            }
        }
    }

    private fun startUpdates() {
        updateJob?.cancel()
        updateJob = viewModelScope.launch(Dispatchers.IO) {
            delay(updatePeriod)
            when (val result = interactor.getUpdatedCoins()) {
                is Result.Error<*> -> {
                    result.errors.errorMessage?.let { it1 ->
                        _uiState.update { UiState.Error(it1) }
                    }
                }
                is Result.Success -> {
                    if (!result.data.isNullOrEmpty()) {
                        val updatedIndexes = mutableListOf<Int>()
                        result.data.forEach { updatedCoin ->
                            val index = _coins.indexOfFirst { it.id == updatedCoin.id }
                            updateCoinPrice(index, updatedCoin)
                            updatedIndexes.add(index)
                        }
                        _uiState.update { UiState.UpdatedData(updatedIndexes) }
                    }
                }
            }
            startUpdates()
        }
    }

    private fun updateCoinPrice(
        index: Int,
        updatedCoin: Coin
    ) {
        _coins[index].priceInUSD = updatedCoin.priceInUSD
    }
}