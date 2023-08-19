package pl.akai.fillist.security.sso.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.akai.fillist.security.sso.models.AccessTokenRequestBody
import pl.akai.fillist.security.sso.models.AccessTokenResponseBody
import pl.akai.fillist.security.sso.models.AuthorizationCodeUrlResponseBody
import reactor.core.publisher.Mono

@Service
class Oauth2SsoService @Autowired constructor(
    val oauth2AuthorizationCodeService: Oauth2AuthorizationCodeService,
    val oauth2TokenService: Oauth2TokenService,
) {

    fun getAuthorizationCodeUrl(state: String): Mono<AuthorizationCodeUrlResponseBody> {
        return oauth2AuthorizationCodeService.getAuthorizationCodeUrl(state)
    }

    fun getAuthorizationToken(requestBody: Mono<AccessTokenRequestBody>): Mono<AccessTokenResponseBody> {
        return oauth2TokenService.getSpotifyToken(requestBody)
    }
}
