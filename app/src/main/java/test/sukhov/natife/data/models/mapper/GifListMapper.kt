package test.sukhov.natife.data.models.mapper

import test.sukhov.natife.data.models.GifItemJson
import test.sukhov.natife.ui.models.GifItemResponse


class GifListMapper {

    fun toGifListResponse(json: List<GifItemJson>?): List<GifItemResponse> {
        with(json) {
            return if (this?.isNotEmpty() == true) {
                this.map { toCryptoItemResponse(it) }
            } else {
                emptyList()
            }
        }
    }

    private fun toCryptoItemResponse(json: GifItemJson): GifItemResponse {
        with(json) {
            return GifItemResponse(
                id = id,
                name = title,
                url = json.images.original.url,
                thumbnailUrl = json.images.previewGif.url
            )
        }
    }
}