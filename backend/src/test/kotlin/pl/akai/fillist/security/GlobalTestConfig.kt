package pl.akai.fillist.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.web.reactive.function.client.WebClient
import pl.akai.fillist.security.models.OAuthParams
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@Configuration
class GlobalTestConfig {

    @Autowired
    private lateinit var oauth2Params: OAuthParams

    @Value("\${fillist.oauth2.client.registration.spotify.secret}")
    private lateinit var spotifySecret: String

    @Bean
    fun spotifyToken(): String {
        return WebClient.create().post()
            .uri("https://accounts.spotify.com/api/token")
            .bodyValue("grant_type=client_credentials")
            .header(HttpHeaders.AUTHORIZATION, getAuthorizationHeader())
            .header("Content-Type", "application/x-www-form-urlencoded")
            .retrieve()
            .bodyToMono(String::class.java)
            .block()!!
    }

    @OptIn(ExperimentalEncodingApi::class)
    private fun getAuthorizationHeader(): String {
        val clientIdAndSecret = "${oauth2Params.clientId}:$spotifySecret"
        val encoded = Base64.encode(clientIdAndSecret.toByteArray())
        return "Basic $encoded"
    }
}
