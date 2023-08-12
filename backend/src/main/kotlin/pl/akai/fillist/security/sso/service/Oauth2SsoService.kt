package pl.akai.fillist.security.sso.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.akai.fillist.security.sso.models.AuthorizationCodeUrlResponseBody

@Service
class Oauth2SsoService @Autowired constructor(val oauth2AuthorizationCodeService: Oauth2AuthorizationCodeService) {

    fun getAuthorizationCodeUrl(state: String): AuthorizationCodeUrlResponseBody {
        return oauth2AuthorizationCodeService.getAuthorizationCodeUrl(state)
    }
}
