package pl.akai.fillist.security.models

import kotlinx.serialization.Serializable

@Serializable
data class AccessTokenResponseBody(
    val accessToken: String,
    val tokenType: String,
    val expiresIn: Int,
    val refreshToken: String,
) {
}
