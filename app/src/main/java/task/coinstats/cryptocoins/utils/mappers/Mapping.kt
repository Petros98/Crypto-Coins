package task.coinstats.cryptocoins.utils.mappers

import task.coinstats.cryptocoins.data.models.entities.Coin
import task.coinstats.cryptocoins.data.models.remote.CoinResponse
import task.coinstats.cryptocoins.utils.Constants

fun CoinResponse.toCoinModel() =
    Coin(
        id = id,
        name = name.orEmpty(),
        iconUrl = iconUrl,
        symbol = S.orEmpty(),
        valueCurrency = Constants.CURRENCY_USD,
        delta24 = delta24 ?: 0.0,
        rank = rank,
        priceInUSD = priceInUSD
    )