package pl.akai.fillist.security.login.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.Link
import org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.linkTo
import org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import pl.akai.fillist.security.login.models.AuthorizationCodeUrlResponseBody
import pl.akai.fillist.security.login.service.Oauth2LoginService
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/oauth2/spotify")
class OAuth2LoginInController @Autowired constructor(
    private val oauth2LoginService: Oauth2LoginService,
) {

    @GetMapping("/authorization")
    fun getAuthorizationCodeUrl(@RequestParam state: String): Mono<EntityModel<AuthorizationCodeUrlResponseBody>> {
        val body = oauth2LoginService.getAuthorizationCodeUrl(state)
        val entityModel: Mono<EntityModel<AuthorizationCodeUrlResponseBody>> = Mono.just(EntityModel.of(body))
        val link: Mono<Link> = linkTo(methodOn(OAuth2LoginInController::class.java).getAuthorizationCodeUrl(state)).withSelfRel().toMono()
        return Mono.zip(entityModel, link).map { it.t1.add(it.t2) }
    }
}
