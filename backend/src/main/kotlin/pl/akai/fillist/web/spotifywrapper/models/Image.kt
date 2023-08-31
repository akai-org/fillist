package pl.akai.fillist.web.spotifywrapper.models

import kotlinx.serialization.Serializable

@Serializable
data class Image(
    val url: String,
    val height: Int?,
    val width: Int?,
)
