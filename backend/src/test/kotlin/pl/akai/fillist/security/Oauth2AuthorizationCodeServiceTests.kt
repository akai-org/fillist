package pl.akai.fillist.security

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import pl.akai.fillist.security.service.Oauth2AuthorizationCodeService

@SpringBootTest
class Oauth2AuthorizationCodeServiceTests {
    @Autowired
    private lateinit var oauth2AuthorizationCodeService: Oauth2AuthorizationCodeService

    @Test
    fun getAuthorizationCodeUrl() {
        oauth2AuthorizationCodeService.getAuthorizationCodeUrl("state").subscribe {
            assert(it.url == "https://accounts.spotify.com/authorize?client_id=clientId&response_type=code&redirect_uri=http://localhost:8080/oauth2/token&state=state")
        }
    }
}
