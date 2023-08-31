package pl.akai.fillist.web.services

import pl.akai.fillist.web.models.UserProfileResponseBody
import pl.akai.fillist.web.spotifywrapper.models.Image
import pl.akai.fillist.web.spotifywrapper.user.SpotifyProfileResponseBody

object UserService {
    fun toUserProfile(spotifyUserProfile: SpotifyProfileResponseBody): UserProfileResponseBody =
        UserProfileResponseBody(
            displayName = spotifyUserProfile.displayName,
            email = spotifyUserProfile.email,
            smallImageUrl = getSmallImage(spotifyUserProfile.images),
            largeImageUrl = getLargeImage(spotifyUserProfile.images),
        )

    private fun getSmallImage(images: List<Image>): String? {
        if (images.isEmpty()) return null
        val min: Int = images.minOf { it.height ?: 0 }
        return images.find { it.height == min }?.url ?: images[0].url
    }

    private fun getLargeImage(images: List<Image>): String? {
        if (images.isEmpty()) return null
        val max: Int = images.maxOf { it.height ?: 0 }
        return images.find { it.height == max }?.url ?: images[0].url
    }
}
