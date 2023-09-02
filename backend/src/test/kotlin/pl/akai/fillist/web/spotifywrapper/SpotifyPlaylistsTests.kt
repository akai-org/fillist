package pl.akai.fillist.web.spotifywrapper

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ContextConfiguration
import pl.akai.fillist.configurations.SpotifyClientConfig
import pl.akai.fillist.configurations.WebTestClientConfig
import pl.akai.fillist.web.spotifywrapper.playlists.SpotifyPlaylistsService

@SpringBootTest()
@Import(WebTestClientConfig::class, SpotifyClientConfig::class)
@ContextConfiguration(classes = [SpotifyClientConfig::class])
class SpotifyPlaylistsTests {
    @Autowired
    private lateinit var spotifyPlaylistsService: SpotifyPlaylistsService

    @Test
    fun getPlaylists() {
        val playlists = spotifyPlaylistsService.getCurrentPlaylists().block()!!
        playlists.items.forEach {
            assertNotNull(it.id)
            assertNotNull(it.name)
            assertNotNull(it.owner)
            assertNotNull(it.images)
            assertNotNull(it.externalUrls)
        }
        assertEquals(playlists.items.size, 20)
    }
}
