package task.coinstats.cryptocoins.data.api

import retrofit2.Response
import retrofit2.http.GET
import task.coinstats.cryptocoins.data.models.remote.CoinsDataResponse
import task.coinstats.cryptocoins.data.models.remote.UpdateCoinsResponse

interface CoinsApiService {

    @GET("coins")
    suspend fun getCoins(): Response<CoinsDataResponse>

    @GET("coins?responseType=array")
    suspend fun getUpdatedCoins(): Response<UpdateCoinsResponse>
}