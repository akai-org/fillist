package pl.akai.fillist.web.playlists

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
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
import pl.akai.fillist.web.models.PlaylistDetails
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

    @Test
    fun getPlaylistDetails() {
        webTestClient.get().uri("/playlists/37i9dQZF1EIUFF8VNSAZXh/details").exchange()
            .expectStatus().isOk.expectBody(PlaylistDetails::class.java).value {
                assertNotNull(it.title)
            }
    }
    @Test
    fun getPlaylistsByName() {
        webTestClient.get().uri("/playlists/name").attribute("name", "test")
            .exchange()
            .expectStatus().isOk.expectBody(PlaylistsResponseBody::class.java).value {
                assertEquals(it.playlists.size, 20)
            }
    }

    @Test
    fun updatePlaylistDetails() {
        val createPlaylistRequestBody = SpotifyCreatePlaylistRequestBody(
            name = "New Playlist",
            description = "New playlist description",
            public = false,
        )
        val playlist = webTestClient.post().uri("/playlists").body(Mono.just(createPlaylistRequestBody))
            .exchange()
            .expectStatus().isOk
            .expectBody(Playlist::class.java)
            .returnResult()
            .responseBody

        assertNotNull(playlist)
        assertNotNull(playlist?.id)

        val updatePlaylistRequestBody = SpotifyCreatePlaylistRequestBody(
            name = "Updated Playlist",
            description = "Updated playlist description",
            public = false,
        )
        webTestClient.put().uri("/playlists/${playlist?.id}").body(Mono.just(updatePlaylistRequestBody))
            .exchange()
            .expectStatus().isOk.expectBody(Playlist::class.java).value {
                assertEquals(it.id, playlist?.id)
                assertEquals(it.name, updatePlaylistRequestBody.name)
                assertEquals(it.public, updatePlaylistRequestBody.public)
            }
    }
}
