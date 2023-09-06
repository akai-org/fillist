package pl.akai.fillist.web.routers

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.router
import pl.akai.fillist.web.handlers.PlaylistHandler

@Configuration
class PlaylistRouter {
    @Bean
    fun playlistRouterFunction(playlistHandler: PlaylistHandler) =
        router {
            GET("/playlists", playlistHandler::getCurrentPlaylists)
            POST("/playlists", playlistHandler::createPlaylist)
        }
}
