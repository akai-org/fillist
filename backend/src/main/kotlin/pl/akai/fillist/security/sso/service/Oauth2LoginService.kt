package pl.akai.fillist.security.sso.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.akai.fillist.security.sso.models.AuthorizationCodeUrlResponseBody
import pl.akai.fillist.security.sso.models.OAuthParams

@Service
class Oauth2LoginService @Autowired constructor(val redirectUrlParams: OAuthParams) {

    fun getAuthorizationCodeUrl(state: String): AuthorizationCodeUrlResponseBody {
        val url = "${redirectUrlParams.authorizationUri}?client_id=${redirectUrlParams.clientId}&response_type=code&redirect_uri=${redirectUrlParams.redirectUri}&scope=${redirectUrlParams.scope}&state=$state"
        return AuthorizationCodeUrlResponseBody(url)
    }
}
