package pl.akai.fillist.web.models

import kotlinx.serialization.Serializable
import pl.akai.fillist.web.spotifywrapper.models.Track

@Serializable
data class PlaylistTracks(
    val href: String,
    val limit: Int,
    val next: String?,
    val offset: Int,
    val previous: String?,
    val total: Int,
    val items: List<Track>,
)
