package pl.akai.fillist.security.oauth.properties

import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class OAuthPropertiesControllerTest {
    @Autowired
    private lateinit var oauthPropertiesController: OauthPropertiesController

    @Autowired
    private lateinit var oauthProperties: OauthProperties

    @Test
    fun contextLoads() {
        assertThat(oauthPropertiesController).isNotNull()
    }

    @Test
    fun getProperties() {
        assertThat(oauthPropertiesController.getProperties().content).isEqualTo(oauthProperties)
    }
}
