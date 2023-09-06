package pl.akai.fillist.web.spotifywrapper.models

import kotlinx.serialization.Serializable

@Serializable
data class Owner(
    val externalUrls: ExternalUrls,
    val displayName: String,
    val id: String,
)
