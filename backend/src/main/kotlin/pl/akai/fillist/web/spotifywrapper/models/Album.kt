package pl.akai.fillist.web.spotifywrapper.models

import kotlinx.serialization.Serializable

@Serializable
data class Album(
    val href: String,
    val id: String,
    val uri: String,
    val name: String,
    val artists: List<Artist>,
    val images: List<Image>? = null,
)
