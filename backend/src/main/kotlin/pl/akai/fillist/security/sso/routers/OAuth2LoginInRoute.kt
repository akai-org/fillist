package pl.akai.fillist.security.sso.routers

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.router
import pl.akai.fillist.security.sso.handlers.OAuth2LoginInHandler

@Configuration
class OAuth2LoginInRoute {
    @Bean
    fun oauth2LoginInRouterFunction(oAuth2LoginInHandler: OAuth2LoginInHandler) =
        router {
            "/oauth2".nest {
                GET("/authorization", oAuth2LoginInHandler::getAuthorizationCodeUrl)
                POST("/token", oAuth2LoginInHandler::getToken)
            }
        }
}
