package pl.akai.fillist.web.spotifywrapper.playlists.models

import kotlinx.serialization.Serializable
import pl.akai.fillist.web.spotifywrapper.models.Image
import pl.akai.fillist.web.spotifywrapper.models.Owner

@Serializable
data class SpotifyPlaylistDetails(
    val name: String,
    val description: String?,
    val cover: Image,
    val owner: Owner,
)
