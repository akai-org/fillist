package pl.akai.fillist.security.oauth.properties

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/oauth/properties")
class OauthPropertiesController @Autowired constructor(val oauthProperties: OauthProperties) {

    @GetMapping
    fun getProperties(): EntityModel<OauthProperties> {
        return EntityModel.of(oauthProperties)
            .add(linkTo(OauthPropertiesController::class.java).withSelfRel())
    }
}
