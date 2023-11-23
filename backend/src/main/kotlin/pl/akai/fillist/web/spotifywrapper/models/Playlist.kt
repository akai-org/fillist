package pl.akai.fillist.web.spotifywrapper.models

import kotlinx.serialization.Serializable

@Serializable
data class Playlist(
    val href: String,
    val id: String,
    val images: List<Image>? = null,
    val name: String,
    val owner: Owner,
)
