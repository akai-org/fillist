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
import pl.akai.fillist.web.spotifywrapper.user.SpotifyUserService

@SpringBootTest()
@Import(SpotifyClientConfig::class)
@ContextConfiguration(classes = [SpotifyClientConfig::class])
class SpotifyUsersTests {
    @Autowired
    private lateinit var spotifyUserService: SpotifyUserService

    @Value("\${fillist.test.spotify.user-id}")
    private lateinit var id: String

    @Test
    fun getProfile() {
        val profile = spotifyUserService.getProfile().block()!!
        assertEquals(profile.id, id)
        assertNotNull(profile.email)
        assertNotNull(profile.displayName)
        assertNotNull(profile)
    }

    @Test
    fun getExternalUserProfile() {
        val userId = "31fnqab3t5khlppaindpem6cpvqi"
        val profile = spotifyUserService.getExternalUserProfile(userId).block()!!
        assertNotNull(profile.id)
        assertNotNull(profile.displayName)
    }
}
