package pl.akai.fillist.security.components

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import pl.akai.fillist.security.service.TokenService
import reactor.core.publisher.Mono

@Component
class AuthenticationManager @Autowired constructor(
    private val tokenService: TokenService,
) : ReactiveAuthenticationManager {
    override fun authenticate(authentication: Authentication?): Mono<Authentication> {
        val authToken = authentication?.credentials.toString()
        return Mono.just(tokenService.validateToken(authToken))
            .filter { it }
            .switchIfEmpty(Mono.empty())
            .map {
                UsernamePasswordAuthenticationToken(
                    tokenService.getSpotifyEmail(authToken),
                    tokenService.getSpotifyAccessToken(authToken),
                    null
                )
            }
    }
}
