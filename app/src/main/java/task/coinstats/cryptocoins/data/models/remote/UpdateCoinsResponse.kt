package task.coinstats.cryptocoins.data.models.remote

import com.squareup.moshi.Json

data class UpdateCoinsResponse(
    @field:Json(name = "coins")
    val coins: List<List<Any>>?
)
