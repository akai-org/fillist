package pl.akai.fillist.web.utils

import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.stereotype.Service
import pl.akai.fillist.web.models.PlaylistsResponseBody
import pl.akai.fillist.web.spotifywrapper.playlists.models.SpotifyPlaylistsResponseBody
import reactor.core.publisher.Mono

@Service
class PlaylistUtils {
    val toPlaylists: (SpotifyPlaylistsResponseBody) -> Mono<PlaylistsResponseBody> = { playlists ->
        Mono.just(playlists)
            .flatMapIterable {
                it.items
            }
            .filterWhen(filterPlaylists).map {
                PlaylistsResponseBody.Playlist(
                    id = it.id,
                    name = it.name,
                    ownerDisplayName = it.owner.displayName,
                    image = this.getLargeImage(it)
                )
            }
            .collectList().map {
                PlaylistsResponseBody(playlists = it)
            }
    }

    private fun getLargeImage(playlist: SpotifyPlaylistsResponseBody.SpotifyPlaylist): String? {
        val max = playlist.images.maxOf { it.height ?: 0 }
        return playlist.images.find { it.height == max }?.url ?: playlist.images.firstOrNull()?.url
    }

    private val filterPlaylists: (SpotifyPlaylistsResponseBody.SpotifyPlaylist) -> Mono<Boolean> = { playlist ->
        ReactiveSecurityContextHolder.getContext().map {
            it.authentication.principal == playlist.owner.id
        }
    }
}