package pl.akai.fillist.web.playlists

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.eq
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.body
import pl.akai.fillist.configurations.SpotifyClientConfig
import pl.akai.fillist.configurations.WebTestClientConfig
import pl.akai.fillist.web.models.Playlist
import pl.akai.fillist.web.models.PlaylistDetails
import pl.akai.fillist.web.models.PlaylistsResponseBody
import pl.akai.fillist.web.spotifywrapper.models.ExternalUrls
import pl.akai.fillist.web.spotifywrapper.models.Image
import pl.akai.fillist.web.spotifywrapper.models.Owner
import pl.akai.fillist.web.spotifywrapper.playlists.SpotifyPlaylistsService
import pl.akai.fillist.web.spotifywrapper.playlists.models.SpotifyCreatePlaylistRequestBody
import pl.akai.fillist.web.spotifywrapper.playlists.models.SpotifyPlaylist
import pl.akai.fillist.web.spotifywrapper.playlists.models.SpotifyPlaylistsResponseBody
import reactor.core.publisher.Mono

@SpringBootTest()
@Import(WebTestClientConfig::class, SpotifyClientConfig::class)
@ContextConfiguration(classes = [SpotifyClientConfig::class])
@AutoConfigureWebTestClient
class PlaylistsRouterTests {
    @Autowired
    private lateinit var webTestClient: WebTestClient

    @MockBean
    private lateinit var playlistsService: SpotifyPlaylistsService

    @Test
    fun getPlaylists() {
        `when`(playlistsService.getCurrentPlaylists(0, 20)).thenReturn(
            Mono.just(
                SpotifyPlaylistsResponseBody(
                    items = listOf(
                        SpotifyPlaylist(
                            id = "id",
                            name = "name",
                            description = "description",
                            public = true,
                            images = listOf(),
                            externalUrls = ExternalUrls("url"),
                            owner = Owner(ExternalUrls(""), "name", "url"),
                        ),
                    ),
                    limit = 20,
                    offset = 0,
                    total = 0,
                ),
            ),
        )
        webTestClient.get().uri("/playlists").exchange()
            .expectStatus().isOk.expectBody(PlaylistsResponseBody::class.java).value {
                assertEquals(it.playlists.size, 1)
                assertEquals(it.playlists[0].name, "name")
                assertEquals(it.playlists[0].description, "description")
                assertEquals(it.playlists[0].public, true)
            }
    }

    @Test
    fun createPlaylist() {
        val createPlaylistRequestBody = SpotifyCreatePlaylistRequestBody(
            name = "New Playlist",
            description = "New playlist description",
            public = false,
        )
        whenever(
            (
                playlistsService.createPlaylist(
                    anyOrNull(),
                    eq(createPlaylistRequestBody),
                )
                ),
        ).thenReturn(
            Mono.just(
                SpotifyPlaylist(
                    id = "id",
                    name = "New Playlist",
                    description = "New playlist description",
                    public = false,
                    images = listOf(
                        Image("url", 100, 100),
                    ),
                    externalUrls = ExternalUrls("url"),
                    owner = Owner(ExternalUrls(""), "name", "url"),
                ),
            ),
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
        `when`(playlistsService.getPlaylist(anyOrNull())).thenReturn(
            Mono.just(
                SpotifyPlaylist(
                    id = "37i9dQZF1EIUFF8VNSAZXh",
                    name = "New Playlist",
                    description = "New playlist description",
                    public = false,
                    images = listOf(
                        Image("url", 100, 100),
                    ),
                    externalUrls = ExternalUrls("url"),
                    owner = Owner(ExternalUrls(""), "name", "url"),
                ),
            ),
        )
        webTestClient.get().uri("/playlists/37i9dQZF1EIUFF8VNSAZXh/details").exchange()
            .expectStatus().isOk.expectBody(PlaylistDetails::class.java).value {
                assertEquals(it.title, "New Playlist")
                assertEquals(it.description, "New playlist description")
                assertNotNull(it.owner)
            }
    }
    @Test
    fun getPlaylistsByName() {
        `when`(playlistsService.getCurrentPlaylists(limit = 999)).thenReturn(
            Mono.just(
                SpotifyPlaylistsResponseBody(
                    total = 1,
                    limit = 999,
                    offset = 0,
                    items = listOf(SpotifyPlaylist(
                            id = "37i9dQZF1EIUFF8VNSAZXh",
                            name = "New Playlist",
                            description = "New playlist description",
                            public = false,
                            images = listOf(
                                Image("url", 100, 100),
                            ),
                            externalUrls = ExternalUrls("url"),
                            owner = Owner(ExternalUrls(""), "name", "url"),
                        ),
                    ),
                ),
            ),
        )

        webTestClient.get().uri("/playlists/name").attribute("name", "New Playlist")
            .exchange()
            .expectStatus().isOk.expectBody(PlaylistsResponseBody::class.java).value {
                assertEquals(1, it.playlists.size)
            }
    }

    @Test
    fun updatePlaylistDetails() {
        val playlistId = "37i9dQZF1EIUFF8VNSAZXh"
        val updatePlaylistRequestBody = SpotifyCreatePlaylistRequestBody(
            name = "New Playlist",
            description = "New playlist description",
            public = false,
        )
        `when`(playlistsService.updatePlaylistDetails(anyOrNull(), eq(updatePlaylistRequestBody))).thenReturn(
            Mono.just(
                SpotifyPlaylist(
                    id = playlistId,
                    name = "New Playlist",
                    description = "New playlist description",
                    public = false,
                    images = listOf(
                        Image("url", 100, 100),
                    ),
                    externalUrls = ExternalUrls("url"),
                    owner = Owner(ExternalUrls(""), "name", "url"),
                ),
            ),
        )
        webTestClient.put().uri("/playlists/$playlistId}").body(Mono.just(updatePlaylistRequestBody))
            .exchange()
            .expectStatus().isOk.expectBody(Playlist::class.java).value {
                assertEquals(it.id, playlistId)
                assertEquals(it.name, updatePlaylistRequestBody.name)
                assertEquals(it.public, updatePlaylistRequestBody.public)
            }
    }
}
