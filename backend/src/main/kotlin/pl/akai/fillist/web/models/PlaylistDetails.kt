package pl.akai.fillist.web.models

import kotlinx.serialization.Serializable
import pl.akai.fillist.web.spotifywrapper.models.OwnerDetails

@Serializable
data class PlaylistDetails(
    val title: String,
    val description: String?,
    val cover: String?,
    val owner: OwnerDetails,
)
