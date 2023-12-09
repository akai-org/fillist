package pl.akai.fillist.web.spotifywrapper.user

import kotlinx.serialization.Serializable
import pl.akai.fillist.web.spotifywrapper.models.Image

@Serializable
data class SpotifyExternalProfileResponseBody(
    val id: String,
    val displayName: String,
    val images: List<Image>,
)
