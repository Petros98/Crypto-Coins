package task.coinstats.cryptocoins.domain

import task.coinstats.cryptocoins.data.models.local.Coin
import task.coinstats.cryptocoins.data.repositories.CoinsRepository
import task.coinstats.cryptocoins.utils.Result
import task.coinstats.cryptocoins.utils.toCoinModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoinsUseCase @Inject constructor(
    private val repository: CoinsRepository
) : CoinsInteractor {

    override suspend fun getCoins(): Result<List<Coin>> {
        return when (val result = repository.getCoins()) {
            is Result.Error<*> -> Result.Error(result.errors)
            is Result.Success -> Result.Success(result.data?.coins?.mapNotNull { it?.toCoinModel() })
        }
    }

    override suspend fun getUpdatedCoins(): Result<List<Coin>> {
        return when (val result = repository.getUpdatedCoins()) {
            is Result.Error<*> -> Result.Error(result.errors)
            is Result.Success -> {
                val updatedCoins = mutableListOf<Coin>()
                result.data?.coins?.forEach {
                    updatedCoins.add(
                        Coin(
                            id = it[0].toString(),
                            priceInUSD = it[2] as? Double
                        )
                    )
                }
                Result.Success(updatedCoins)
            }
        }
    }
}