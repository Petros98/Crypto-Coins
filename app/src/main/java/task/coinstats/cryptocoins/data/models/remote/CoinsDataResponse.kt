package task.coinstats.cryptocoins.data.models.remote

import com.squareup.moshi.Json

data class CoinsDataResponse(

	@field:Json(name="coins")
	val coins: List<CoinResponse?>?
)

