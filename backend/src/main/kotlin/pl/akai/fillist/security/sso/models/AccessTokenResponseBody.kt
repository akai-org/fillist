package pl.akai.fillist.security.sso.models

import SpotifyAccessTokenResponseBody
import kotlinx.serialization.Serializable

@Serializable
class AccessTokenResponseBody(
    val accessToken: String,
    val tokenType: String,
    val expiresIn: Int,
    val refreshToken: String,
) {
    constructor(spotifyAccessTokenResponseBody: SpotifyAccessTokenResponseBody) : this(
        accessToken = spotifyAccessTokenResponseBody.accessToken,
        tokenType = spotifyAccessTokenResponseBody.tokenType,
        expiresIn = spotifyAccessTokenResponseBody.expiresIn,
        refreshToken = spotifyAccessTokenResponseBody.refreshToken,
    ) {}
}
