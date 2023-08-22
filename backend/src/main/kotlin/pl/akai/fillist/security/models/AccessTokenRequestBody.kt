package pl.akai.fillist.security.models

import kotlinx.serialization.Serializable

@Serializable
data class AccessTokenRequestBody(val code: String)
