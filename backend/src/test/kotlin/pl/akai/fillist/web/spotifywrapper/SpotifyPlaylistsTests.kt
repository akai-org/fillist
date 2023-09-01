package pl.akai.fillist.web.spotifywrapper

import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.ClassPathResource
import pl.akai.fillist.security.GlobalTestConfig
import pl.akai.fillist.security.models.OAuthParams
import pl.akai.fillist.web.spotifywrapper.playlists.SpotifyPlaylistsService
import pl.akai.fillist.web.spotifywrapper.playlists.models.SpotifyPlaylistsResponseBody

@SpringBootTest(classes = [SpotifyPlaylistsService::class, GlobalTestConfig::class, OAuthParams::class])
class SpotifyPlaylistsTests {
    @Autowired
    private lateinit var spotifyPlaylistsService: SpotifyPlaylistsService

    @Autowired
    private lateinit var spotifyJson: Json

    @Test
    fun getPlaylists() {
        val playlists = spotifyPlaylistsService.getCurrentPlaylists().block()!!
        val file = ClassPathResource("responses/playlists.json").file.readText()
        val validResponse = spotifyJson.decodeFromString<SpotifyPlaylistsResponseBody>(file)
        assertNotNull(playlists)
        assertEquals(validResponse, playlists)
    }
}
