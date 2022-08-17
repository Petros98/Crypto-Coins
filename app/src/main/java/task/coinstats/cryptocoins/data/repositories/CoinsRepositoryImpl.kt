package task.coinstats.cryptocoins.data.repositories

import task.coinstats.cryptocoins.data.api.CoinsApiService
import task.coinstats.cryptocoins.data.models.remote.CoinsDataResponse
import task.coinstats.cryptocoins.data.models.remote.UpdateCoinsResponse
import task.coinstats.cryptocoins.utils.CallException
import task.coinstats.cryptocoins.utils.Result
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoinsRepositoryImpl @Inject constructor(
    private val apiService: CoinsApiService
) : CoinsRepository {

    override suspend fun getCoins(): Result<CoinsDataResponse?> {
        try {
            val response = apiService.getCoins()
            return if (response.isSuccessful)
                Result.Success(response.body())
            else
                Result.Error(
                    CallException(
                        errorMessage = response.message(),
                        errorCode = response.code(),
                        errorBody = response.body()
                    )
                )
        } catch (e: Throwable) {
            return Result.Error(
                CallException<Nothing>(
                    errorMessage = e.message,
                    errorCode = 0
                )
            )
        }
    }

    override suspend fun getUpdatedCoins(): Result<UpdateCoinsResponse?> {
        try {
            val response = apiService.getUpdatedCoins()
            return if (response.isSuccessful)
                Result.Success(response.body())
            else
                Result.Error(
                    CallException(
                        errorMessage = response.message(),
                        errorCode = response.code(),
                        errorBody = response.body()
                    )
                )
        } catch (e: Throwable) {
           return Result.Error(
                CallException<Nothing>(
                    errorMessage = e.message,
                    errorCode = 0
                )
            )
        }
    }
}