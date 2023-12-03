package pl.akai.fillist.web.spotifywrapper.user

import kotlinx.serialization.Serializable
import pl.akai.fillist.web.spotifywrapper.models.*

@Serializable
data class SpotifySearchResponseBody(
    val tracks: PaginationHeader<Track>? = null,
    val artists: PaginationHeader<Artist>? = null,
    val albums: PaginationHeader<Album>? = null,
    val playlists: PaginationHeader<Playlist>? = null,
)
