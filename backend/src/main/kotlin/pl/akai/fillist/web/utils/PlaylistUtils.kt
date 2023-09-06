package pl.akai.fillist.web.utils

import pl.akai.fillist.web.models.PlaylistsResponseBody
import pl.akai.fillist.web.spotifywrapper.playlists.models.SpotifyPlaylist
import pl.akai.fillist.web.spotifywrapper.playlists.models.SpotifyPlaylistsResponseBody
import reactor.core.publisher.Mono

object PlaylistUtils {
    val toPlaylists: (SpotifyPlaylistsResponseBody) -> Mono<PlaylistsResponseBody> = { playlists ->
        Mono.just(playlists)
            .flatMapIterable {
                it.items
            }
            .map {
                PlaylistsResponseBody.Playlist(
                    id = it.id,
                    name = checkNameLengthAndFix(it.name),
                    ownerDisplayName = it.owner.displayName,
                    image = this.getLargeImage(it),
                )
            }
            .collectList().map {
                PlaylistsResponseBody(
                    limit = playlists.limit,
                    offset = playlists.offset,
                    total = playlists.total,
                    playlists = it,
                )
            }
    }

    private fun getLargeImage(playlist: SpotifyPlaylist): String? {
        if (playlist.images.isEmpty()) return null
        val max = playlist.images.maxOf { it.height ?: 0 }
        return playlist.images.find { it.height == max }?.url ?: playlist.images.firstOrNull()?.url
    }

    private fun checkNameLengthAndFix(name: String): String {
        val maxLength = 30
        if (name.length > maxLength) {
            val words = name.substring(0, maxLength).split(" ")
            if (words.size == 1) return "${name.substring(0, maxLength - 3)}..."
            return "${words.subList(0, words.size - 2).joinToString(" ")}..."
        }
        return name
    }
}
