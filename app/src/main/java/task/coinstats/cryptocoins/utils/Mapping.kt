package task.coinstats.cryptocoins.utils

import task.coinstats.cryptocoins.data.models.local.Coin
import task.coinstats.cryptocoins.data.models.remote.CoinResponse

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