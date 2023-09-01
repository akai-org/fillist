package pl.akai.fillist.web.spotifywrapper.playlists.models

import kotlinx.serialization.Serializable
import pl.akai.fillist.web.spotifywrapper.models.Image

@Serializable
data class SpotifyPlaylistsResponseBody(
    val items: List<SpotifyPlaylist>,
) {
    @Serializable
    data class SpotifyPlaylist (
        val description: String,
        val externalUrls: ExternalUrls,
        val id: String,
        val images: List<Image>,
        val name: String,
        val owner: Owner,
    ){
        @Serializable
        data class ExternalUrls (
            val spotify: String,
        )

        @Serializable
        data class Owner(
            val externalUrls: ExternalUrls,
            val displayName: String,
            val id: String,
        )
    }
}
