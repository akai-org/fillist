package pl.akai.fillist.web.spotifywrapper.models

import kotlinx.serialization.Serializable

@Serializable
data class OwnerDetails(
    val id: String,
    val name: String,
    val picture: String?,
)
