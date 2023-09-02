package pl.akai.fillist.configurations

import AccessTokenResponseBody
import kotlinx.serialization.json.Json
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.codec.json.KotlinSerializationJsonDecoder
import org.springframework.web.reactive.function.client.WebClient
import pl.akai.fillist.security.models.OAuthParams
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@Configuration
class SpotifyTokenConfig {
    @Autowired
    private lateinit var oauth2Params: OAuthParams

    @Autowired
    private lateinit var spotifyJson: Json

    @Value("\${fillist.oauth2.client.registration.spotify.secret}")
    private lateinit var spotifySecret: String

    @Bean
    fun spotifyToken(): AccessTokenResponseBody {
        return WebClient.builder().codecs {
            it.defaultCodecs().kotlinSerializationJsonDecoder(KotlinSerializationJsonDecoder(spotifyJson))
        }.build().post().uri("https://accounts.spotify.com/api/token").bodyValue("grant_type=client_credentials")
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationHeader())
            .header("Content-Type", "application/x-www-form-urlencoded").retrieve()
            .bodyToMono(AccessTokenResponseBody::class.java).block()!!
    }

    @OptIn(ExperimentalEncodingApi::class)
    private fun getAuthorizationHeader(): String {
        val clientIdAndSecret = "${oauth2Params.clientId}:$spotifySecret"
        val encoded = Base64.encode(clientIdAndSecret.toByteArray())
        return "Basic $encoded"
    }
}
