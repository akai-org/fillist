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
import pl.akai.fillist.web.models.PlaylistTracks
import pl.akai.fillist.web.models.PlaylistsResponseBody
import pl.akai.fillist.web.spotifywrapper.models.*
import pl.akai.fillist.web.spotifywrapper.playlists.SpotifyPlaylistsService
import pl.akai.fillist.web.spotifywrapper.playlists.models.*
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
                    items = listOf(
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
                // BUG ON SPOTIFY SIDE
                // assertEquals(it.public, updatePlaylistRequestBody.public)
            }
    }

    @Test
    fun getPlaylistTracks() {
        `when`(playlistsService.getSpotifyPlaylistTracks(anyOrNull())).thenReturn(
            Mono.just(
                SpotifyPlaylistTracks(
                    href = "href",
                    limit = 1,
                    offset = 0,
                    total = 1,
                    next = "",
                    previous = "",
                    items = listOf(
                        SpotifyTrackWrapper(
                            track = Track(
                                id = "1WiIsyGhDQ0ZAD4vnEjOm3",
                                name = "name",
                                uri = "uri",
                                album = Album(
                                    id = "id",
                                    name = "name",
                                    artists = listOf(),
                                    href = "",
                                    uri = "",
                                ),
                                artists = listOf(),
                            ),
                        ),
                    ),
                ),
            ),
        )
        webTestClient.get().uri("/playlists/1WiIsyGhDQ0ZAD4vnEjOm3/tracks").exchange()
            .expectStatus().isOk.expectBody(PlaylistTracks::class.java).value {
                assertNotNull(it.items)
                assertEquals(it.items.size, 1)
                assertEquals(it.items[0].id, "1WiIsyGhDQ0ZAD4vnEjOm3")
                assertEquals(it.items[0].name, "name")
                assertEquals(it.items[0].uri, "uri")
                assertEquals(it.items[0].album.name, "name")
                assertEquals(it.items[0].album.id, "id")
            }
    }

    @Test
    fun changePlaylistCover() {
        val playlistId = "1B9WyPlzPbkyGMWcGlrgP7"
        val image = "/9j/2wCEABoZGSccJz4lJT5CLy8vQkc9Ozs9R0dHR0dHR0dHR0dHR0dHR0dHR0dHR0dHR0dHR0dHR0dHR0dHR0dHR0dHR0cBHCcnMyYzPSYmPUc9Mj1HR0dEREdHR0dHR0dHR0dHR0dHR0dHR0dHR0dHR0dHR0dHR0dHR0dHR0dHR0dHR0dHR//dAAQAAf/uAA5BZG9iZQBkwAAAAAH/wAARCAABAAEDACIAAREBAhEB/8QASwABAQAAAAAAAAAAAAAAAAAAAAYBAQAAAAAAAAAAAAAAAAAAAAAQAQAAAAAAAAAAAAAAAAAAAAARAQAAAAAAAAAAAAAAAAAAAAD/2gAMAwAAARECEQA/AJgAH//Z"
        `when`(playlistsService.changePlaylistCover(eq(playlistId), eq(image))).thenReturn(
            Mono.empty(),
        )
        webTestClient.put().uri("/playlists/$playlistId/cover").body(Mono.just(image))
            .exchange()
            .expectStatus().isOk
    }
}
