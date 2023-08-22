package pl.akai.fillist.security.models

import SpotifyAccessTokenResponseBody
import kotlinx.serialization.Serializable
import pl.akai.fillist.security.service.TokenService

@Serializable
class AccessTokenResponseBody(
    val accessToken: String,
    val tokenType: String,
    val expiresIn: Int,
    val refreshToken: String,
) {
    constructor(
        spotifyAccessTokenResponseBody: SpotifyAccessTokenResponseBody,
        tokenService: TokenService,
    ) : this(
        accessToken = tokenService.generateFillistAccessToken(spotifyAccessTokenResponseBody),
        tokenType = "Bearer",
        expiresIn = spotifyAccessTokenResponseBody.expiresIn,
        refreshToken = tokenService.generateFillistRefreshToken(spotifyAccessTokenResponseBody),
    ) {
    }
}
