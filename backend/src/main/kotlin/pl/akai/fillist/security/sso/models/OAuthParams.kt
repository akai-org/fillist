package pl.akai.fillist.security.sso.models

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

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

    @Value("\${fillist.oauth2.client.registration.spotify.spotify-idp-uri}")
    lateinit var spotifyIdpUri: String

    fun toMultiValueMap(): MultiValueMap<String, String> {
        val map: MultiValueMap<String, String> = LinkedMultiValueMap()
        map.add("client_id", clientId)
        map.add("redirect_uri", redirectUri)
        map.add("scope", scope)
        return map
    }
}
