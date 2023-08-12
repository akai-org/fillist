package pl.akai.fillist.web.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.hateoas.config.EnableHypermediaSupport
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.WebFluxConfigurer

@Configuration
@EnableWebFlux
@EnableHypermediaSupport(type = [EnableHypermediaSupport.HypermediaType.HAL])
class WebFluxConfig : WebFluxConfigurer {
    @Value("\${fillist.allow-origin}")
    private val allowedOrigins: String = ""

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins(allowedOrigins)
            .allowedMethods("*")
            .maxAge(3600)
    }
}
