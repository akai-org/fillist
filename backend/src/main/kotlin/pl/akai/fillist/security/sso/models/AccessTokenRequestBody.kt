package pl.akai.fillist.security.sso.models

import kotlinx.serialization.Serializable

@Serializable
data class AccessTokenRequestBody(val code: String)
