package pl.akai.fillist.web.handlers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import pl.akai.fillist.web.spotifywrapper.user.SpotifyUserService
import pl.akai.fillist.web.utils.UserUtils
import reactor.core.publisher.Mono

@Component
class UserHandler @Autowired constructor(
    private val spotifyUserService: SpotifyUserService,
) {
    fun getUserInfo(request: ServerRequest): Mono<ServerResponse> {
        val body = spotifyUserService.getProfile().map(UserUtils::toUserProfile)
        return ServerResponse.ok().body(body)
    }
}
