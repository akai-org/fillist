package pl.akai.fillist.security

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import pl.akai.fillist.security.models.OAuthParams
import pl.akai.fillist.security.service.Oauth2AuthorizationCodeService

@SpringBootTest
class Oauth2AuthorizationCodeServiceTests {
    @Autowired
    private lateinit var oauth2AuthorizationCodeService: Oauth2AuthorizationCodeService

    @Autowired
    private lateinit var oAuthParams: OAuthParams

    @Test
    fun getAuthorizationCodeUrl() {
        oauth2AuthorizationCodeService.getAuthorizationCodeUrl("state").block()?.let {
            val spotifyIdpUrl = it.url.split("?")[0]
            val queryParams = it.url.split("?")[1].split("&").map { it.split("=")[0] to it.split("=")[1] }.toMap()
            assertEquals("${oAuthParams.spotifyIdpUri}/authorize", spotifyIdpUrl)
            assertEquals(queryParams["client_id"], oAuthParams.clientId)
            assertEquals(queryParams["redirect_uri"], oAuthParams.redirectUri)
            assertEquals(queryParams["scope"], oAuthParams.scope)
        }
    }
}
