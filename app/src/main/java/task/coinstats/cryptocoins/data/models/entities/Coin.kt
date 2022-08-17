package task.coinstats.cryptocoins.data.models.entities

data class Coin(
    val id: String,
    var priceInUSD: Double?,
    val name: String = "",
    val iconUrl: String? = null,
    val valueCurrency: String = "",
    val delta24: Double = 0.0,
    val rank: Int = 0,
    val symbol: String = ""
)
