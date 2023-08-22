package pl.akai.fillist.security.configurations

import kotlinx.serialization.json.Json
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.codec.json.KotlinSerializationJsonDecoder
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.web.reactive.function.client.ClientRequest
import org.springframework.web.reactive.function.client.ExchangeFunction
import org.springframework.web.reactive.function.client.WebClient


@Configuration
class WebClientConfig {
    @Value("\${fillist.spotify-api-uri}")
    val spotifyApiUri: String = ""

    @Bean
    fun spotifyClient(): WebClient {
        return WebClient.builder()
            .baseUrl(spotifyApiUri)
            .defaultHeader(HttpHeaders.ACCEPT, "application/json")
            .codecs {
                val decoder = KotlinSerializationJsonDecoder(Json { ignoreUnknownKeys = true })
                it.defaultCodecs().kotlinSerializationJsonDecoder(decoder)
            }
            .filter { request: ClientRequest, next: ExchangeFunction ->
                ReactiveSecurityContextHolder.getContext()
                    .map {
                        it.authentication.credentials.toString()
                    }
                    .defaultIfEmpty("")
                    .flatMap {
                        println(it)
                        next.exchange(
                            ClientRequest.from(request)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer ${it}")
                                .build()
                        )
                    }
            }
            .build()
    }


}
