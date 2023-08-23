package pl.akai.fillist.web.spotifywrapper.user

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class SpotifyUserService @Autowired constructor(
    private val spotifyClient: WebClient,
) {

    companion object {
        const val PROFILE_ENDPOINT = "/me"
    }

    fun getProfile(): Mono<SpotifyProfileResponseBody> {
        return spotifyClient.get().uri(PROFILE_ENDPOINT).retrieve()
            .bodyToMono(SpotifyProfileResponseBody::class.java)
    }

    fun getProfile(token: String): Mono<SpotifyProfileResponseBody> {
        return spotifyClient.get().uri(PROFILE_ENDPOINT).header(HttpHeaders.AUTHORIZATION, "Bearer $token").retrieve()
            .bodyToMono(SpotifyProfileResponseBody::class.java)
    }
}
