package pl.akai.fillist.web.spotifywrapper.models

import kotlinx.serialization.Serializable

@Serializable
data class Artist(
    val href: String,
    val id: String,
    val images: List<Image>? = null,
    val name: String,
    val uri: String,
)
