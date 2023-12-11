package pl.akai.fillist.web.handlers

import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.bodyToMono
import pl.akai.fillist.web.spotifywrapper.playlists.SpotifyPlaylistsService
import pl.akai.fillist.web.spotifywrapper.playlists.models.SpotifyCreatePlaylistRequestBody
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

    fun createPlaylist(serverRequest: ServerRequest): Mono<ServerResponse> {
        val requestBody = serverRequest.bodyToMono(SpotifyCreatePlaylistRequestBody::class.java)
        val userId = ReactiveSecurityContextHolder.getContext().map { it.authentication.principal.toString() }
        val responseBody = Mono.zip(requestBody, userId).flatMap {
            spotifyPlaylistsService.createPlaylist(it.t2, it.t1)
        }.map {
            PlaylistUtils.toPlaylist(it)
        }
        return ServerResponse.ok().body(responseBody)
    }

    fun getPlaylistDetails(serverRequest: ServerRequest): Mono<ServerResponse> {
        val playlistId = serverRequest.pathVariable("playlist-id")
        val body = spotifyPlaylistsService.getPlaylist(playlistId).flatMap(PlaylistUtils.toPlaylistDetails)
        return ServerResponse.ok().body(body)
    }

    fun updatePlaylistDetails(serverRequest: ServerRequest): Mono<ServerResponse> {
        val playlistId = serverRequest.pathVariable("playlist-id")
        val requestBody = serverRequest.bodyToMono(SpotifyCreatePlaylistRequestBody::class.java)
        val responseBody = Mono.zip(requestBody, Mono.just(playlistId)).flatMap {
            spotifyPlaylistsService.updatePlaylistDetails(it.t2, it.t1)
        }.map {
            PlaylistUtils.toPlaylist(it)
        }
        return ServerResponse.ok().body(responseBody)
    }
}
