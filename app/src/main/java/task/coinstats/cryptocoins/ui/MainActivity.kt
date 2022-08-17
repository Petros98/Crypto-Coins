package task.coinstats.cryptocoins.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import task.coinstats.cryptocoins.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()
    private val coinsAdapter by lazy { CoinsAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)

            it.coinsRv.adapter = coinsAdapter
            it.swipeRefreshLayout.setOnRefreshListener {
                viewModel.onRefresh()
            }
        }

        viewModel.getData()

        listenDataChanges()
    }

    private fun listenDataChanges() {
        viewModel.uiState.onEach { uiState ->
            when (uiState) {
                is UiState.Coins -> {
                    coinsAdapter.submitList(uiState.data)
                }
                is UiState.Error -> {
                    Toast.makeText(this, uiState.message, Toast.LENGTH_SHORT).show()
                }
                UiState.Loading -> {
                    binding.swipeRefreshLayout.isRefreshing = true
                }
                UiState.Uninitialized -> {}
                is UiState.UpdatedData -> {
                    uiState.indexes.forEach {
                        coinsAdapter.notifyItemChanged(it)
                    }
                }
            }
            if (uiState !is UiState.Loading) {
                binding.swipeRefreshLayout.isRefreshing = false
            }
        }.launchIn(lifecycleScope)
    }
}