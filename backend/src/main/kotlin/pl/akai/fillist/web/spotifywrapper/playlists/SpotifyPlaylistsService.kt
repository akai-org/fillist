package pl.akai.fillist.web.spotifywrapper.playlists

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import pl.akai.fillist.web.spotifywrapper.playlists.models.SpotifyCreatePlaylistRequestBody
import pl.akai.fillist.web.spotifywrapper.playlists.models.SpotifyPlaylist
import pl.akai.fillist.web.spotifywrapper.playlists.models.SpotifyPlaylistTracks
import pl.akai.fillist.web.spotifywrapper.playlists.models.SpotifyPlaylistsResponseBody
import reactor.core.publisher.Mono

@Service
class SpotifyPlaylistsService @Autowired constructor(
    private val spotifyClient: WebClient,
) {
    fun getCurrentPlaylists(offset: Int = 0, limit: Int = 20): Mono<SpotifyPlaylistsResponseBody> {
        return spotifyClient.get().uri("/me/playlists?offset=$offset&limit=$limit").retrieve()
            .bodyToMono(SpotifyPlaylistsResponseBody::class.java)
    }

    fun createPlaylist(userId: String, spotifyCreatePlaylistRequestBody: SpotifyCreatePlaylistRequestBody): Mono<SpotifyPlaylist> {
        return spotifyClient.post().uri("/users/$userId/playlists")
            .body(Mono.just(spotifyCreatePlaylistRequestBody), SpotifyCreatePlaylistRequestBody::class.java)
            .retrieve()
            .bodyToMono(SpotifyPlaylist::class.java)
    }

    fun getPlaylist(playlistId: String): Mono<SpotifyPlaylist> {
        return spotifyClient.get().uri("/playlists/$playlistId").retrieve()
            .bodyToMono(SpotifyPlaylist::class.java)
    }

    fun updatePlaylistDetails(playlistId: String, spotifyCreatePlaylistRequestBody: SpotifyCreatePlaylistRequestBody): Mono<SpotifyPlaylist> {
        return spotifyClient.put().uri("/playlists/$playlistId")
            .body(Mono.just(spotifyCreatePlaylistRequestBody), SpotifyCreatePlaylistRequestBody::class.java)
            .retrieve()
            .bodyToMono(SpotifyPlaylist::class.java)
            .then(getPlaylist(playlistId))
    }

    fun getSpotifyPlaylistTracks(playlistId: String): Mono<SpotifyPlaylistTracks> {
        return spotifyClient.get().uri("/playlists/$playlistId/tracks").retrieve()
            .bodyToMono(SpotifyPlaylistTracks::class.java)
    }

    fun changePlaylistCover(playlistId: String, imageData: String): Mono<Void> {
        return spotifyClient.put().uri("/playlists/$playlistId/images")
            .bodyValue(imageData)
            .retrieve()
            .toBodilessEntity()
            .then()
    }
}
