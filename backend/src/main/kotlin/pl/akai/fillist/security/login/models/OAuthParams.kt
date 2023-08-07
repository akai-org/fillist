package pl.akai.fillist.security.login.models

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class OAuthParams {
    @Value("\${fillist.oauth2.client.registration.spotify.client-id}")
    lateinit var clientId: String

    @Value("\${fillist.oauth2.client.registration.spotify.authorization-uri}")
    lateinit var authorizationUri: String

    @Value("\${fillist.oauth2.client.registration.spotify.redirect-uri}")
    lateinit var redirectUri: String

    @Value("\${fillist.oauth2.client.registration.spotify.scope}")
    lateinit var scope: String
}
