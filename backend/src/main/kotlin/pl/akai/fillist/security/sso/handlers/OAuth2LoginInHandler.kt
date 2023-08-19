package pl.akai.fillist.security.sso.handlers

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyExtractors
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import pl.akai.fillist.security.sso.models.AccessTokenRequestBody
import pl.akai.fillist.security.sso.models.AuthorizationCodeUrlResponseBody
import pl.akai.fillist.security.sso.service.Oauth2SsoService
import reactor.core.publisher.Mono

@Component
class OAuth2LoginInHandler @Autowired constructor(
    private val oauth2SsoService: Oauth2SsoService,
) {

    companion object {
        val LOGGER = LoggerFactory.getLogger(OAuth2LoginInHandler::class.java)
    }

    fun getAuthorizationCodeUrl(request: ServerRequest): Mono<ServerResponse> {
        val state = request.queryParam("state").get()
        val body = oauth2SsoService.getAuthorizationCodeUrl(state)
        return ServerResponse.ok().body(body, AuthorizationCodeUrlResponseBody::class.java)
    }

    fun getToken(request: ServerRequest): Mono<ServerResponse> {
        val requestBody = request.body(BodyExtractors.toMono(AccessTokenRequestBody::class.java))
        val response = oauth2SsoService.getAuthorizationToken(requestBody)
        return response
            .flatMap { ServerResponse.ok().bodyValue(it) }
            .onErrorResume {
                LOGGER.warn("Error during getting token", it)
                ServerResponse.status(HttpStatus.UNAUTHORIZED).build()
            }
    }
}
