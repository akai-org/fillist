package pl.akai.fillist.web.models

import kotlinx.serialization.Serializable

@Serializable
data class UserProfileResponseBody (
    val displayName: String,
    val email: String,
    val smallImageUrl: String?,
    val largeImageUrl: String?,
)
