package pl.akai.fillist.security.models

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenRequestBody(
    private val grantType: String,
    private val refreshToken: String
)
