package pl.akai.fillist.configurations

import AccessTokenResponseBody
import kotlinx.serialization.json.Json
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.http.HttpHeaders
import org.springframework.http.codec.json.KotlinSerializationJsonDecoder
import org.springframework.web.reactive.function.client.WebClient
import pl.akai.fillist.security.models.OAuthParams
import pl.akai.fillist.security.models.RefreshTokenRequestBody
import pl.akai.fillist.security.service.Oauth2TokenService
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@Configuration
@Import(SpotifyClientConfig::class)
class SpotifyTokenConfig {

    @Autowired
    private lateinit var spotifyJson: Json

    @Autowired
    private lateinit var oauth2Params: OAuthParams

    @Value("\${fillist.oauth2.client.registration.spotify.secret}")
    private lateinit var spotifySecret: String

    @Value("\${fillist.test.spotify.refresh-token}")
    private val refreshToken: String = ""

    @Bean
    fun spotifyToken(): AccessTokenResponseBody {
        val requestBody =
            RefreshTokenRequestBody(
                refreshToken = refreshToken,
                grantType = "refresh_token",
            )
        return getSpotifyRefreshToken(requestBody)
    }

    fun getSpotifyRefreshToken(requestBody: RefreshTokenRequestBody): AccessTokenResponseBody {
        return WebClient.builder()
            .baseUrl("${oauth2Params.spotifyIdpUri}${Oauth2TokenService.SPOTIFY_TOKEN_ENDPOINT}")
            .codecs {
                it.defaultCodecs().kotlinSerializationJsonDecoder(KotlinSerializationJsonDecoder(spotifyJson))
            }
            .build()
            .post()
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationHeader())
            .header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded")
            .bodyValue(requestBody.toLinkedMultiValueMap())
            .retrieve()
            .bodyToMono(AccessTokenResponseBody::class.java).block()!!
    }

    @OptIn(ExperimentalEncodingApi::class)
    private fun getAuthorizationHeader(): String {
        val clientIdAndSecret = "${oauth2Params.clientId}:$spotifySecret"
        val encoded = Base64.encode(clientIdAndSecret.toByteArray())
        return "Basic $encoded"
    }
}
