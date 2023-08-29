package pl.akai.fillist.web.routers

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.router
import pl.akai.fillist.web.handlers.UserHandler

@Configuration
class UserRouter {
    @Bean
    fun userRouterFunction(userHandler: UserHandler) =
        router {
            GET("/me", userHandler::getUserInfo)
        }
}
