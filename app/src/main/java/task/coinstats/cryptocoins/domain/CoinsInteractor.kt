package task.coinstats.cryptocoins.domain

import task.coinstats.cryptocoins.data.models.local.Coin
import task.coinstats.cryptocoins.utils.Result

interface CoinsInteractor {

    suspend fun getCoins(): Result<List<Coin>>

    suspend fun getUpdatedCoins(): Result<List<Coin>>
}