package pl.akai.fillist.security.sso.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class AccessTokenResponseBody (
    @SerialName("access_token")
    val accessToken: String,
    @SerialName("token_type")
    val tokenType: String,
    @SerialName("expires_in")
    val expiresIn: Int,
    @SerialName("refresh_token")
    val refreshToken: String,
    val scope: String,
)

