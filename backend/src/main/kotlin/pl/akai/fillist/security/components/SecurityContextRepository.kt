package pl.akai.fillist.security.components

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextImpl
import org.springframework.security.web.server.context.ServerSecurityContextRepository
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono


@Component
class SecurityContextRepository @Autowired constructor(
    private val authenticationManager: AuthenticationManager,
) : ServerSecurityContextRepository {

    override fun save(exchange: ServerWebExchange?, context: SecurityContext?): Mono<Void> {
        TODO("Not yet implemented")
    }

    override fun load(exchange: ServerWebExchange?): Mono<SecurityContext> {
        return Mono.justOrEmpty(exchange?.request?.headers?.getFirst(HttpHeaders.AUTHORIZATION)).filter { authHeader ->
            authHeader.startsWith("Bearer ")
        }.flatMap {
            val token = it.substring(7)
            val auth = UsernamePasswordAuthenticationToken("", token)
            this.authenticationManager.authenticate(auth).map { authentication -> SecurityContextImpl(authentication) }
        }
    }

}
