package pl.akai.fillist.security.oauth.properties

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class OauthProperties {
    @Value("\${fillist.oauth2.client.registration.spotify.client-id}")
    lateinit var clientId: String

    @Value("\${fillist.oauth2.client.registration.spotify.scope}")
    lateinit var scopes: List<String>
}
