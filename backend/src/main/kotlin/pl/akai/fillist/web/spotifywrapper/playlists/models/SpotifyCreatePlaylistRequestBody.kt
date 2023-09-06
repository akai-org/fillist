package pl.akai.fillist.web.spotifywrapper.playlists.models

import kotlinx.serialization.Serializable

@Serializable
data class SpotifyCreatePlaylistRequestBody(
    val name: String,
    val description: String,
    val public: Boolean,
)
