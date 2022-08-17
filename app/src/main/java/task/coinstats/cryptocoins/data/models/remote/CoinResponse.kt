package task.coinstats.cryptocoins.data.models.remote

import com.squareup.moshi.Json

data class CoinResponse(

    @field:Json(name = "a")
    val A: Double?,

    @field:Json(name = "p1")
    val p1: Double?,

    @field:Json(name = "tu")
    val tu: String?,

    @field:Json(name = "ru")
    val ru: String?,

    @field:Json(name = "pu")
    val priceInUSD: Double,

    @field:Json(name = "p7")
    val p7: Double?,

    @field:Json(name = "ls")
    val ls: Double?,

    @field:Json(name = "i")
    val id: String,

    @field:Json(name = "m")
    val M: Long?,

    @field:Json(name = "pop24")
    val pop24: Double?,

    @field:Json(name = "n")
    val name: String?,

    @field:Json(name = "ds")
    val ds: Double?,

    @field:Json(name = "p")
    val P: Int?,

    @field:Json(name = "cs")
    val cs: Double?,

    @field:Json(name = "r")
    val rank: Int,

    @field:Json(name = "pb")
    val pb: Double?,

    @field:Json(name = "s")
    val S: String?,

    @field:Json(name = "p24")
    val delta24: Double?,

    @field:Json(name = "t")
    val T: Double?,

    @field:Json(name = "csd")
    val csd: Boolean?,

    @field:Json(name = "v")
    val V: Double?,

    @field:Json(name = "w")
    val W: String?,

    @field:Json(name = "ic")
    val iconUrl: String?,

    @field:Json(name = "exp")
    val exp: List<String?>?
)