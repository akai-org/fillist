package pl.akai.fillist.web.spotifywrapper

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ContextConfiguration
import pl.akai.fillist.configurations.SpotifyClientConfig

@SpringBootTest
@Import(SpotifyClientConfig::class)
@ContextConfiguration(classes = [SpotifyClientConfig::class])
class SpotifySearchTests {
    @Autowired
    private lateinit var spotifySearchService: SpotifySearchService

    @Value("\${fillist.test.spotify.user-id}")
    private val userId: String = ""

    @Test
    fun searchTrack() {
        val res = spotifySearchService.search(
            SpotifySearchQueryFilters(track = "Lemon Tree", types = listOf(SpotifySearchType.TRACK)),
        ).block()
        assertTrue(res?.tracks?.items?.isNotEmpty()!!)
        assertTrue(res.tracks?.items?.first()?.name?.contains("Lemon Tree", ignoreCase = true)!!)
        assertTrue(res.tracks?.items?.first()?.artists?.first()?.name?.contains("Fools Garden", ignoreCase = true)!!)
    }

    @Test
    fun searchAlbum() {
        val res = spotifySearchService.search(
            SpotifySearchQueryFilters(album = "Hulanki", types = listOf(SpotifySearchType.ALBUM)),
        ).block()
        assertTrue(res?.albums?.items?.isNotEmpty()!!)
        assertTrue(res.albums?.items?.first()?.name?.contains("Hulanki", ignoreCase = true)!!)
        assertTrue(res.albums?.items?.first()?.artists?.first()?.name?.contains("Young Leosia", ignoreCase = true)!!)
    }

    @Test
    fun searchArtist() {
        val res = spotifySearchService.search(
            SpotifySearchQueryFilters(artist = "Young Leosia", types = listOf(SpotifySearchType.ARTIST)),
        ).block()
        assertTrue(res?.artists?.items?.isNotEmpty()!!)
        assertTrue(res.artists?.items?.first()?.name?.contains("Young Leosia", ignoreCase = true)!!)
    }

    @Test
    fun hasToHaveAtLeastOneSearchType() {
        assertThrows<IllegalArgumentException> {
            spotifySearchService.search(
                SpotifySearchQueryFilters(artist = "Young Leosia", types = listOf()),
            ).block()
        }
    }

    @Test
    fun searchTypesNotProvidedAreNotPresent() {
        val res = spotifySearchService.search(
            SpotifySearchQueryFilters(artist = "Young Leosia", types = listOf(SpotifySearchType.ARTIST)),
        ).block()
        assertTrue(res?.albums == null)
        assertTrue(res?.tracks == null)
        assertTrue(res?.playlists == null)
    }

    @Test
    fun searchingBothTrackAndArtist() {
        val res = spotifySearchService.search(
            SpotifySearchQueryFilters(genericFilter = "Tokyo", types = listOf(SpotifySearchType.ARTIST, SpotifySearchType.TRACK)),
        ).block()
        assertTrue(res?.artists?.items?.isNotEmpty()!!)
        assertTrue(res.tracks?.items?.isNotEmpty()!!)
    }
}
