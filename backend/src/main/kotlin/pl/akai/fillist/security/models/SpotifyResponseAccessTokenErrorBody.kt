package pl.akai.fillist.security.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpotifyResponseAccessTokenErrorBody(
    val error: String,
    @SerialName("error_description")
    val errorDescription: String,
)
