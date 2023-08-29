package pl.akai.fillist.web.services

import pl.akai.fillist.web.models.UserProfileResponseBody
import pl.akai.fillist.web.spotifywrapper.user.SpotifyProfileResponseBody

object UserService {
    fun toUserProfile(spotifyUserProfile: SpotifyProfileResponseBody): UserProfileResponseBody =
        UserProfileResponseBody(
            displayName = spotifyUserProfile.displayName,
            email = spotifyUserProfile.email,
            smallImageUrl = getSmallImage(spotifyUserProfile.images),
            largeImageUrl = getLargeImage(spotifyUserProfile.images),
        )

    private fun getSmallImage(images: List<SpotifyProfileResponseBody.Image>): String {
        val min: Int = images.minOf { it.height }
        return images.find { it.height == min }?.url ?: ""
    }

    private fun getLargeImage(images: List<SpotifyProfileResponseBody.Image>): String {
        val max: Int = images.maxOf { it.height }
        return images.find { it.height == max }?.url ?: ""
    }

}
