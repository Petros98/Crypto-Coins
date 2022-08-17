package task.coinstats.cryptocoins.ui

import task.coinstats.cryptocoins.data.models.entities.Coin

sealed class UiState {
    object Uninitialized: UiState()

    object Loading: UiState()

    data class Error(val message: String): UiState()

    data class Coins(val data: List<Coin>): UiState()

    data class UpdatedData(val indexes: List<Int>): UiState()
}
