package pl.akai.fillist.web.models

import kotlinx.serialization.Serializable

@Serializable
data class PlaylistsResponseBody(
    val playlists: List<Playlist>,
) {
    @Serializable
    data class Playlist(
        val name: String,
        val id: String,
        val image: String?,
        val ownerDisplayName: String,
    )
}
