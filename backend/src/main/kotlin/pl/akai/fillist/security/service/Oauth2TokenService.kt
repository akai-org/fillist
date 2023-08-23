package pl.akai.fillist.security.service

import AccessTokenResponseBody
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatusCode
import org.springframework.http.codec.json.KotlinSerializationJsonDecoder
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient
import pl.akai.fillist.security.exceptions.InvalidGrantException
import pl.akai.fillist.security.models.AccessTokenRequestBody
import pl.akai.fillist.security.models.OAuthParams
import pl.akai.fillist.security.models.RefreshTokenRequestBody
import pl.akai.fillist.security.models.SpotifyResponseAccessTokenErrorBody
import reactor.core.publisher.Mono
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@Service
class Oauth2TokenService @Autowired constructor(
    private val oauth2Params: OAuthParams,
    private val tokenService: TokenService,
) {

    companion object {
        const val SPOTIFY_TOKEN_ENDPOINT = "/api/token"
    }

    @Value("\${fillist.oauth2.client.registration.spotify.secret}")
    private val spotifySecret: String = ""

    @OptIn(ExperimentalSerializationApi::class)
    private val webClient =
        WebClient.builder()
            .baseUrl("${oauth2Params.spotifyIdpUri}$SPOTIFY_TOKEN_ENDPOINT")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded")
            .codecs {
                val decoder = KotlinSerializationJsonDecoder(
                    Json {
                        ignoreUnknownKeys = true
                        namingStrategy = JsonNamingStrategy.SnakeCase
                    },
                )
                it.defaultCodecs().kotlinSerializationJsonDecoder(decoder)
            }.build()

    fun getSpotifyToken(requestBody: Mono<AccessTokenRequestBody>): Mono<AccessTokenResponseBody> {
        return requestBody.flatMap {
            this.webClient.post()
                .header(HttpHeaders.AUTHORIZATION, getAuthorizationHeader())
                .bodyValue(it.toLinkedMultiValueMap())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, getToken4xxErrorHandling)
                .bodyToMono(AccessTokenResponseBody::class.java)
                .flatMap { response ->
                    tokenService.generateTokensResponse(response)
                }
        }
    }

    fun getRefreshToken(requestBody: Mono<RefreshTokenRequestBody>): Mono<AccessTokenResponseBody> {
        return requestBody
            .map {
                RefreshTokenRequestBody(
                    it.grantType,
                    this.tokenService.getSpotifyRefreshToken(it.refreshToken)
                )
            }
            .flatMap {
                this.webClient.post()
                    .header(HttpHeaders.AUTHORIZATION, getAuthorizationHeader())
                    .bodyValue(it.toLinkedMultiValueMap())
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, getToken4xxErrorHandling)
                    .bodyToMono(AccessTokenResponseBody::class.java)
                    .flatMap { response ->
                        response.refreshToken = it.refreshToken
                        tokenService.generateTokensResponse(response)
                    }
            }
    }

    @OptIn(ExperimentalEncodingApi::class)
    private fun getAuthorizationHeader(): String {
        val clientIdAndSecret = "${oauth2Params.clientId}:$spotifySecret"
        val encoded = Base64.encode(clientIdAndSecret.toByteArray())
        return "Basic $encoded"
    }

    private val getToken4xxErrorHandling: (ClientResponse) -> Mono<InvalidGrantException> = { clientResponse ->
        clientResponse.bodyToMono(SpotifyResponseAccessTokenErrorBody::class.java)
            .flatMap { Mono.error(InvalidGrantException(it.errorDescription)) }
    }
}
