package test.sukhov.natife.ui.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GifItemResponse(
    val id: String,
    val name: String,
    val url: String,
    val thumbnailUrl: String
) : Parcelable
