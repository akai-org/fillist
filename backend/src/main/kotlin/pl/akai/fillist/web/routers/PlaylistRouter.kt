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
            GET("/playlists/name", playlistHandler::getCurrentPlaylistsByName)
            GET("/playlists/{playlist-id}/details", playlistHandler::getPlaylistDetails)
            // ATTENTION: Update don't work properly (Spotify API bug) (state 2023-03-17)
            PUT("/playlists/{playlist-id}", playlistHandler::updatePlaylistDetails)
            GET("/playlists/{playlist-id}/tracks", playlistHandler::getPlaylistTracks)
            PUT("/playlists/{playlist-id}/cover", playlistHandler::changePlaylistCover)
        }
}
