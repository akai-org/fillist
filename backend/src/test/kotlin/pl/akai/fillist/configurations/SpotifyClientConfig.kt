package pl.akai.fillist.configurations

import AccessTokenResponseBody
import kotlinx.serialization.json.Json
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpHeaders
import org.springframework.http.codec.json.KotlinSerializationJsonDecoder
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.DefaultUriBuilderFactory

@TestConfiguration
class SpotifyClientConfig {
    @Value("\${fillist.spotify-api-uri}")
    val spotifyApiUri: String = ""

    @Autowired
    private lateinit var spotifyToken: AccessTokenResponseBody

    @Autowired
    private lateinit var spotifyJson: Json

    @Bean
    fun spotifyClient(): WebClient {
        val factory = DefaultUriBuilderFactory(spotifyApiUri)
        factory.encodingMode = DefaultUriBuilderFactory.EncodingMode.URI_COMPONENT
        val size = 16 * 1024 * 1024
        val strategies = ExchangeStrategies.builder()
            .codecs { clientDefaultCodecsConfigurer ->
                clientDefaultCodecsConfigurer.defaultCodecs().maxInMemorySize(size)
            }
            .build()
        return WebClient.builder().uriBuilderFactory(factory).defaultHeader(HttpHeaders.ACCEPT, "application/json")
            .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer ${spotifyToken.accessToken}").codecs {
                it.defaultCodecs().kotlinSerializationJsonDecoder(KotlinSerializationJsonDecoder(spotifyJson))
            }
            .exchangeStrategies(strategies)
            .build()
    }
}
