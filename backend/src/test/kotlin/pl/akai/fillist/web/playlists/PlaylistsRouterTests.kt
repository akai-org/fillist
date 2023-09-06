package pl.akai.fillist.web.playlists

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.body
import pl.akai.fillist.configurations.SpotifyClientConfig
import pl.akai.fillist.configurations.WebTestClientConfig
import pl.akai.fillist.web.models.Playlist
import pl.akai.fillist.web.models.PlaylistsResponseBody
import pl.akai.fillist.web.spotifywrapper.playlists.models.SpotifyCreatePlaylistRequestBody
import reactor.core.publisher.Mono

@SpringBootTest()
@Import(WebTestClientConfig::class, SpotifyClientConfig::class)
@ContextConfiguration(classes = [SpotifyClientConfig::class])
@AutoConfigureWebTestClient
class PlaylistsRouterTests {
    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Test
    fun getPlaylists() {
        webTestClient.get().uri("/playlists").exchange()
            .expectStatus().isOk.expectBody(PlaylistsResponseBody::class.java).value {
                assertEquals(it.playlists.size, 20)
            }
    }

    @Test
    fun createPlaylist() {
        val createPlaylistRequestBody = SpotifyCreatePlaylistRequestBody(
            name = "New Playlist",
            description = "New playlist description",
            public = false,
        )
        webTestClient.post().uri("/playlists").body(Mono.just(createPlaylistRequestBody))
            .exchange()
            .expectStatus().isOk.expectBody(Playlist::class.java).value {
                assertEquals(it.name, createPlaylistRequestBody.name)
                assertEquals(it.public, createPlaylistRequestBody.public)
            }
    }
}
