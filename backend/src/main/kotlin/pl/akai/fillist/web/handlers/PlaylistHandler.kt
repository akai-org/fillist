package pl.akai.fillist.web.handlers

import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import pl.akai.fillist.web.spotifywrapper.playlists.SpotifyPlaylistsService
import pl.akai.fillist.web.spotifywrapper.playlists.models.SpotifyCreatePlaylistRequestBody
import pl.akai.fillist.web.spotifywrapper.playlists.models.SpotifyPlaylistsResponseBody
import pl.akai.fillist.web.spotifywrapper.user.SpotifyUserService
import pl.akai.fillist.web.utils.PlaylistUtils
import reactor.core.publisher.Mono

@Component
class PlaylistHandler(
    private val spotifyPlaylistsService: SpotifyPlaylistsService,
    private val spotifyUserService: SpotifyUserService
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

    fun getCurrentPlaylistsByName(serverRequest: ServerRequest): Mono<ServerResponse> {
        val name = serverRequest.queryParam("name").orElse("")
        val body = spotifyPlaylistsService
            .getCurrentPlaylists(limit = 999)
            .map { it ->
                SpotifyPlaylistsResponseBody(
                    it.total,
                    it.limit,
                    it.offset,
                    it.items.filter { it.name == name }
                )
            }
            .flatMap(PlaylistUtils.toPlaylists)
        return ServerResponse.ok().body(body)
    }

    fun getPlaylistDetails(serverRequest: ServerRequest): Mono<ServerResponse> {
        val playlistId = serverRequest.pathVariable("playlist-id")
        val playlistDetails = spotifyPlaylistsService.getPlaylist(playlistId).flatMap(PlaylistUtils.toPlaylistDetails)
        val userDetails = playlistDetails.flatMap {
            spotifyUserService.getExternalUserProfile(it.owner.id)
        }
        return playlistDetails.zipWith(userDetails)
            .flatMap {
                val picture = if (it.t2.images.isNotEmpty()) {
                    it.t2.images[0].url
                } else {
                    null
                }
                it.t1.owner.picture = picture
                ServerResponse.ok().body(Mono.just(it.t1))
            }
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
