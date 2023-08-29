package pl.akai.fillist.web.spotifywrapper.user

import kotlinx.serialization.Serializable

@Serializable
data class SpotifyProfileResponseBody(
    val email: String,
    val displayName: String,
    val images: List<Image>,
) {
    @Serializable
    data class Image(
        val url: String,
        val height: Int,
        val width: Int,
    )
}
