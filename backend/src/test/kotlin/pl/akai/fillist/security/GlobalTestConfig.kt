package pl.akai.fillist.security

import AccessTokenResponseBody
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.http.HttpHeaders
import org.springframework.http.codec.json.KotlinSerializationJsonDecoder
import org.springframework.web.reactive.function.client.ClientRequest
import org.springframework.web.reactive.function.client.ExchangeFunction
import org.springframework.web.reactive.function.client.WebClient
import pl.akai.fillist.security.models.OAuthParams
import java.net.URI
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@Configuration
class GlobalTestConfig {

    @Value("\${fillist.spotify-api-uri}")
    val spotifyApiUri: String = ""

    @Autowired
    private lateinit var oauth2Params: OAuthParams

    @Value("\${fillist.oauth2.client.registration.spotify.secret}")
    private lateinit var spotifySecret: String

    @OptIn(ExperimentalSerializationApi::class)
    @Bean
    @Qualifier("spotifyJson")
    fun spotifyJson(): Json {
        return Json {
            ignoreUnknownKeys = true
            namingStrategy = JsonNamingStrategy.SnakeCase
        }
    }

    @Bean
    fun spotifyToken(): AccessTokenResponseBody {
        return WebClient.builder().codecs {
            it.defaultCodecs().kotlinSerializationJsonDecoder(KotlinSerializationJsonDecoder(spotifyJson()))
        }.build().post().uri("https://accounts.spotify.com/api/token").bodyValue("grant_type=client_credentials")
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationHeader())
            .header("Content-Type", "application/x-www-form-urlencoded").retrieve()
            .bodyToMono(AccessTokenResponseBody::class.java).block()!!
    }

    @Bean
    @Primary
    fun spotifyClient(): WebClient {
        return WebClient.builder().baseUrl(spotifyApiUri).defaultHeader(HttpHeaders.ACCEPT, "application/json")
            .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer ${spotifyToken().accessToken}").codecs {
                it.defaultCodecs().kotlinSerializationJsonDecoder(KotlinSerializationJsonDecoder(spotifyJson()))
            }
            .filter { request: ClientRequest, next: ExchangeFunction ->
                next.exchange(
                    ClientRequest.from(request).url(
                        URI(request.url().toString().replace("me", "users/smedjan"))
                    )
                        .build(),
                )
            }
            .build()
    }

    @OptIn(ExperimentalEncodingApi::class)
    private fun getAuthorizationHeader(): String {
        val clientIdAndSecret = "${oauth2Params.clientId}:$spotifySecret"
        val encoded = Base64.encode(clientIdAndSecret.toByteArray())
        return "Basic $encoded"
    }
}
