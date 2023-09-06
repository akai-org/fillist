package pl.akai.fillist.web.spotifywrapper

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ContextConfiguration
import pl.akai.fillist.configurations.SpotifyClientConfig
import pl.akai.fillist.web.spotifywrapper.playlists.SpotifyPlaylistsService
import pl.akai.fillist.web.spotifywrapper.playlists.models.SpotifyCreatePlaylistRequestBody

@SpringBootTest()
@Import(SpotifyClientConfig::class)
@ContextConfiguration(classes = [SpotifyClientConfig::class])
class SpotifyPlaylistsTests {
    @Autowired
    private lateinit var spotifyPlaylistsService: SpotifyPlaylistsService

    @Value("\${fillist.test.spotify.user-id}")
    private val userId: String = ""

    @Test
    fun getPlaylists() {
        val playlists = spotifyPlaylistsService.getCurrentPlaylists().block()!!
        assertNotNull(playlists.limit)
        assertNotNull(playlists.offset)
        assertNotNull(playlists.total)
        playlists.items.forEach {
            assertNotNull(it.id)
            assertNotNull(it.name)
            assertNotNull(it.owner)
            assertNotNull(it.images)
            assertNotNull(it.externalUrls)
        }
        assertEquals(playlists.items.size, 20)
    }

    @Test
    fun createPlaylistsTestResponseModel() {
        val createPlaylistRequestBody = SpotifyCreatePlaylistRequestBody(
            name = "New Playlist",
            description = "New playlist description",
            public = false,
        )
        val playlist = spotifyPlaylistsService.createPlaylist(userId, createPlaylistRequestBody).block()!!
        assertNotNull(playlist)
        assertEquals(playlist.name, createPlaylistRequestBody.name)
        assertEquals(playlist.description, createPlaylistRequestBody.description)
        assertEquals(playlist.owner.id, userId)
    }
}
