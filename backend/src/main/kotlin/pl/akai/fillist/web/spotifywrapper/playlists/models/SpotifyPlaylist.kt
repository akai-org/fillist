package pl.akai.fillist.web.spotifywrapper.playlists.models

import kotlinx.serialization.Serializable
import pl.akai.fillist.web.spotifywrapper.models.ExternalUrls
import pl.akai.fillist.web.spotifywrapper.models.Image
import pl.akai.fillist.web.spotifywrapper.models.Owner

@Serializable
data class SpotifyPlaylist(
    val description: String?,
    val externalUrls: ExternalUrls,
    val id: String,
    val images: List<Image>?,
    val name: String,
    val owner: Owner,
    val public: Boolean,
)
