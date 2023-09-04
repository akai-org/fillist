package pl.akai.fillist.web.handlers

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import pl.akai.fillist.web.spotifywrapper.playlists.SpotifyPlaylistsService
import pl.akai.fillist.web.utils.PlaylistUtils
import reactor.core.publisher.Mono

@Component
class PlaylistHandler(
    private val spotifyPlaylistsService: SpotifyPlaylistsService,
) {
    fun getCurrentPlaylists(serverRequest: ServerRequest): Mono<ServerResponse> {
        val limit = serverRequest.queryParam("limit").orElse("20").toInt()
        val offset = serverRequest.queryParam("offset").orElse("0").toInt()
        val body = spotifyPlaylistsService.getCurrentPlaylists(offset, limit).flatMap(PlaylistUtils.toPlaylists)
        return ServerResponse.ok().body(body)
    }
}
