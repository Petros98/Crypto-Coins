package task.coinstats.cryptocoins.data.repositories

import task.coinstats.cryptocoins.data.models.remote.CoinsDataResponse
import task.coinstats.cryptocoins.data.models.remote.UpdateCoinsResponse
import task.coinstats.cryptocoins.utils.Result

interface CoinsRepository {

    suspend fun getCoins(): Result<CoinsDataResponse?>

    suspend fun getUpdatedCoins(): Result<UpdateCoinsResponse?>
}