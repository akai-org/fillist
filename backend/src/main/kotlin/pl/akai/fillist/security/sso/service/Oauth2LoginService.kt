package pl.akai.fillist.security.sso.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.util.UriComponentsBuilder
import pl.akai.fillist.security.sso.models.AuthorizationCodeUrlResponseBody
import pl.akai.fillist.security.sso.models.OAuthParams

@Service
class Oauth2LoginService @Autowired constructor(val redirectUrlParams: OAuthParams) {

    fun getAuthorizationCodeUrl(state: String): AuthorizationCodeUrlResponseBody {
        val url = UriComponentsBuilder.fromUriString(redirectUrlParams.authorizationUri)
            .queryParam("response_type", "code")
            .queryParam("state", state)
            .queryParams(redirectUrlParams.toMultiValueMap())
            .build()
            .toUriString()
        return AuthorizationCodeUrlResponseBody(url)
    }
}
