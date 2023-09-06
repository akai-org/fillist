package pl.akai.fillist.web.models

import kotlinx.serialization.Serializable

@Serializable
data class PlaylistsResponseBody(
    val total: Int,
    val limit: Int,
    val offset: Int,
    val playlists: List<Playlist>,
)
