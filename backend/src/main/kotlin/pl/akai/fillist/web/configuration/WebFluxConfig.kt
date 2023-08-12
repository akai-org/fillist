package pl.akai.fillist.web.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.hateoas.config.EnableHypermediaSupport
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.WebFluxConfigurer

@Configuration
@EnableWebFlux
@EnableHypermediaSupport(type = [EnableHypermediaSupport.HypermediaType.HAL])
class WebFluxConfig : WebFluxConfigurer
