package pl.akai.fillist.security.login.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import pl.akai.fillist.security.login.models.AuthorizationCodeUrlResponseBody
import pl.akai.fillist.security.login.service.Oauth2LoginService
import reactor.core.publisher.Mono

@RestController
@RequestMapping("oauth2/spotify")
class OAuth2LoginInController @Autowired constructor(
    private val oauth2LoginService: Oauth2LoginService,
) {

    @GetMapping("/authorization")
    fun getAuthorizationCodeUrl(@RequestParam state: String): Mono<ServerResponse> {
        val body = oauth2LoginService.getAuthorizationCodeUrl(state)
        return ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(body), AuthorizationCodeUrlResponseBody::class.java)
    }
}
