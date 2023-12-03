package pl.akai.fillist.web.spotifywrapper.models

import kotlinx.serialization.Serializable

@Serializable
data class Track(
    val name: String,
    val id: String,
    val uri: String,
    val album: Album,
    val year: Int,
    val artists: List<Artist>? = null,
)
