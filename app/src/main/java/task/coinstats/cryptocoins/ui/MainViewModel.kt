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
import task.coinstats.cryptocoins.data.models.entities.Coin
import task.coinstats.cryptocoins.data.repositories.CoinsRepository
import task.coinstats.cryptocoins.utils.Result
import task.coinstats.cryptocoins.utils.mappers.toCoinModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: CoinsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Uninitialized)
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
            _uiState.update { UiState.Loading }
            when (val result = repository.getCoins()) {
                is Result.Error<*> -> {
                    result.errors.errorMessage?.let { it1 ->
                        _uiState.update { UiState.Error(it1) }
                    }
                }
                is Result.Success -> {
                    if (result.data?.coins != null && result.data.coins.isNotEmpty()) {
                        _coins = result.data.coins.mapNotNull { it?.toCoinModel() }
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
            when (val result = repository.getUpdatedCoins()) {
                is Result.Error<*> -> {
                    result.errors.errorMessage?.let { error ->
                        _uiState.update { UiState.Error(error) }
                    }
                }
                is Result.Success -> {
                    if (result.data?.coins != null && result.data.coins.isNotEmpty()) {
                        val updatedIndexes = getUpdatedIndexes(result.data.coins)
                        _uiState.update { UiState.UpdatedData(updatedIndexes) }
                    }
                }
            }
            startUpdates()
        }
    }

    private fun getUpdatedIndexes(coins: List<List<Any>>): List<Int> {
        val updatedCoins = mutableListOf<Coin>()
        coins.forEach {
            updatedCoins.add(
                Coin(
                    id = it[0].toString(),
                    priceInUSD = it[2] as? Double
                )
            )
        }
        val updatedIndexes = mutableListOf<Int>()
        updatedCoins.forEach { updatedCoin ->
            val index = _coins.indexOfFirst { it.id == updatedCoin.id }
            updateCoinPrice(index, updatedCoin)
            updatedIndexes.add(index)
        }
        return updatedIndexes
    }

    private fun updateCoinPrice(
        index: Int,
        updatedCoin: Coin
    ) {
        _coins[index].priceInUSD = updatedCoin.priceInUSD
    }
}