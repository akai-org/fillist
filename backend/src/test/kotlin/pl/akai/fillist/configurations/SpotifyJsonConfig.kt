package pl.akai.fillist.configurations

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SpotifyJsonConfig {
    @OptIn(ExperimentalSerializationApi::class)
    @Bean
    fun spotifyJson(): Json {
        return Json {
            ignoreUnknownKeys = true
            namingStrategy = JsonNamingStrategy.SnakeCase
        }
    }
}
