package test.sukhov.natife.data.models

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class GifItemJson(
    @Json(name = "id") val id: String,
    @Json(name = "url") val url: String,
    @Json(name = "title") val title: String,
    @Json(name = "images") val images: Images
) : Parcelable {

    @JsonClass(generateAdapter = true)
    @Parcelize
    data class Images(
        @Json(name = "original") val original: Image,
        @Json(name = "preview_gif") val previewGif: Image
    ) : Parcelable {

        @JsonClass(generateAdapter = true)
        @Parcelize
        data class Image(
            @Json(name = "url") val url: String
        ) : Parcelable
    }
}
