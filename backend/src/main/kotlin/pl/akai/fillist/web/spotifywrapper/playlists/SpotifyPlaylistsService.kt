package pl.akai.fillist.web.spotifywrapper.playlists

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
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
}
