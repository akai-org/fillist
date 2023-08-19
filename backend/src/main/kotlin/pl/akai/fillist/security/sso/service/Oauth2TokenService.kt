package pl.akai.fillist.security.sso.service

import SpotifyAccessTokenResponseBody
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient
import pl.akai.fillist.security.sso.exceptions.InvalidGrantException
import pl.akai.fillist.security.sso.models.*
import reactor.core.publisher.Mono
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@Service
class Oauth2TokenService @Autowired constructor(private val oauth2Params: OAuthParams) {

    companion object {
        const val SPOTIFY_TOKEN_ENDPOINT = "/api/token"
    }

    @Value("\${fillist.oauth2.client.registration.spotify.secret}")
    private val spotifySecret: String = ""

    private val webClient =
        WebClient.builder().baseUrl("${oauth2Params.spotifyIdpUri}${SPOTIFY_TOKEN_ENDPOINT}").build()

    fun getSpotifyToken(requestBody: Mono<AccessTokenRequestBody>): Mono<AccessTokenResponseBody> {
        return requestBody.flatMap {
            val spotifyRequestBody = SpotifyRequestAccessTokenBody(
                grantType = "authorization_code",
                code = it.code,
                redirectUri = oauth2Params.redirectUri,
            )
            this.webClient.post()
                .header("Authorization", getAuthorizationHeader())
                .header("Content-Type", "application/x-www-form-urlencoded")
                .bodyValue(spotifyRequestBody.toLinkedMultiValueMap())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, getToken4xxErrorHandling)
                .bodyToMono(SpotifyAccessTokenResponseBody::class.java)
                .flatMap { response -> Mono.just(AccessTokenResponseBody(response)) }
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
