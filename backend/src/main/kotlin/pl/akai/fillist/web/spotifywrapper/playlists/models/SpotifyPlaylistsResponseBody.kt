package pl.akai.fillist.web.spotifywrapper.playlists.models

import kotlinx.serialization.Serializable

@Serializable
data class SpotifyPlaylistsResponseBody(
    val total: Int,
    val limit: Int,
    val offset: Int,
    val items: List<SpotifyPlaylist>,
)
