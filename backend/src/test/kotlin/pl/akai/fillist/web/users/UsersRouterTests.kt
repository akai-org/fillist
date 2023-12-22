package pl.akai.fillist.web.users

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.WebTestClient
import pl.akai.fillist.configurations.SpotifyClientConfig
import pl.akai.fillist.configurations.WebTestClientConfig
import pl.akai.fillist.web.models.UserProfileResponseBody
import pl.akai.fillist.web.spotifywrapper.user.SpotifyUserService

@SpringBootTest()
@Import(SpotifyClientConfig::class, WebTestClientConfig::class)
@ContextConfiguration(classes = [SpotifyClientConfig::class])
@AutoConfigureWebTestClient
class UsersRouterTests {
    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Autowired
    private lateinit var spotifyUserService: SpotifyUserService

    @Test
    fun getUserProfile() {
        val spotifyResponse = spotifyUserService.getProfile().block()!!
        webTestClient.get().uri("/me").exchange()
            .expectStatus().isOk.expectBody(UserProfileResponseBody::class.java).value {
                assertEquals(it.email, spotifyResponse.email)
                assertEquals(it.displayName, spotifyResponse.displayName)
            }
    }
}
