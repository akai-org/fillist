package pl.akai.fillist.security.login.models

import kotlinx.serialization.Serializable

@Serializable
data class AuthorizationCodeUrlResponseBody(
    val url: String,
)
