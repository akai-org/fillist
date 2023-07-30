package pl.akai.fillist.security.oauth.properties

import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class OauthPropertiesTest {
    @Autowired
    private lateinit var oauthProperties: OauthProperties

    @Test
    fun clientId() {
        assertThat(oauthProperties.clientId).isEqualTo("test-id")
    }

    @Test
    fun scopes() {
        assertThat(oauthProperties.scopes).isEqualTo(listOf("scope1", "scope2"))
    }
}
