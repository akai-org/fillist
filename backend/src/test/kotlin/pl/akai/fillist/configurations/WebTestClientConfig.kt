package pl.akai.fillist.configurations

import AccessTokenResponseBody
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.web.reactive.server.WebTestClientBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.http.HttpHeaders
import org.springframework.test.web.reactive.server.WebTestClient
import pl.akai.fillist.security.service.TokenService
import pl.akai.fillist.web.spotifywrapper.models.Image
import pl.akai.fillist.web.spotifywrapper.user.SpotifyProfileResponseBody
import pl.akai.fillist.web.spotifywrapper.user.SpotifyUserService
import reactor.core.publisher.Mono

@TestConfiguration
class WebTestClientConfig {

    @Autowired
    private lateinit var spotifyToken: AccessTokenResponseBody

    @Autowired
    private lateinit var tokenService: TokenService

    @Value("\${fillist.test.spotify.user-id}")
    private lateinit var id: String

    @MockBean
    lateinit var spotifyUserService: SpotifyUserService

    @Bean
    @Primary
    fun webTestClientBuilderCustomizer(): WebTestClientBuilderCustomizer {
        return WebTestClientBuilderCustomizer { builder: WebTestClient.Builder ->
            builder.defaultHeader(
                HttpHeaders.AUTHORIZATION,
                "Bearer ${getFillistToken(spotifyToken)}",
            )
        }
    }

    private fun getFillistToken(spotifyToken: AccessTokenResponseBody): String {
        this.configMock()
        return tokenService.generateFillistAccessToken(spotifyToken).block()!!
    }

    fun configMock() {
        Mockito.`when`(spotifyUserService.getProfile(spotifyToken.accessToken)).thenReturn(
            Mono.just(
                SpotifyProfileResponseBody(
                    id,
                    "email",
                    "displayName",
                    listOf(
                        Image("url", 100, 100),
                    ),
                ),
            ),
        )
    }
}
