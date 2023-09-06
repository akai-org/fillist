package pl.akai.fillist.configurations

import AccessTokenResponseBody
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.web.reactive.server.WebTestClientBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.http.HttpHeaders
import org.springframework.test.web.reactive.server.WebTestClient
import pl.akai.fillist.security.service.TokenService

@TestConfiguration
class WebTestClientConfig {

    @Autowired
    private lateinit var spotifyToken: AccessTokenResponseBody

    @Autowired
    private lateinit var tokenService: TokenService

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
        return tokenService.generateFillistAccessToken(spotifyToken).block()!!
    }

}
