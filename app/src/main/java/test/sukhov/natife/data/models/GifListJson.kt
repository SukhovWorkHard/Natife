package test.sukhov.natife.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class GifListJson(
    @Json(name = "data") val data: List<GifItemJson>? = emptyList()
)
