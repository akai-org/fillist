package pl.akai.fillist.security.configurations

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity.AuthorizeExchangeSpec
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.HttpStatusServerEntryPoint
import pl.akai.fillist.security.components.AuthenticationManager
import pl.akai.fillist.security.components.SecurityContextRepository

@Configuration
@EnableWebFluxSecurity
class WebSecurityConfig {

    @Autowired
    lateinit var securityContextRepository: SecurityContextRepository

    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    @Bean
    fun apiHttpSecurity(http: ServerHttpSecurity): SecurityWebFilterChain {
        http
            .authorizeExchange { exchanges: AuthorizeExchangeSpec ->
                exchanges.pathMatchers("/oauth2/**").permitAll()
                exchanges.anyExchange().authenticated()
            }
            .csrf { it.disable() }
            .formLogin { it.disable() }
            .httpBasic { it.authenticationEntryPoint(HttpStatusServerEntryPoint(HttpStatus.UNAUTHORIZED)) }
            .securityContextRepository(securityContextRepository)
            .authenticationManager(authenticationManager)
        return http.build()
    }
}
