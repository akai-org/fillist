package pl.akai.fillist.security.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.util.UriComponentsBuilder
import pl.akai.fillist.security.models.AuthorizationCodeUrlResponseBody
import pl.akai.fillist.security.models.OAuthParams
import reactor.core.publisher.Mono

@Service
class Oauth2AuthorizationCodeService @Autowired constructor(val oauth2Params: OAuthParams) {

    companion object {
        const val SPOTIFY_AUTHORIZE_ENDPOINT = "/authorize"
    }

    fun getAuthorizationCodeUrl(state: String): Mono<AuthorizationCodeUrlResponseBody> {
        val url = UriComponentsBuilder.fromUriString("${oauth2Params.spotifyIdpUri}$SPOTIFY_AUTHORIZE_ENDPOINT")
            .queryParam("response_type", "code")
            .queryParam("state", state)
            .queryParams(oauth2Params.toMultiValueMap())
            .build()
            .toUriString()
        return Mono.just(AuthorizationCodeUrlResponseBody(url))
    }
}
