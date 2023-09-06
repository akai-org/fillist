package pl.akai.fillist.web.playlists

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import pl.akai.fillist.web.models.PlaylistsResponseBody
import pl.akai.fillist.web.spotifywrapper.models.ExternalUrls
import pl.akai.fillist.web.spotifywrapper.models.Image
import pl.akai.fillist.web.spotifywrapper.models.Owner
import pl.akai.fillist.web.spotifywrapper.playlists.models.SpotifyPlaylist
import pl.akai.fillist.web.spotifywrapper.playlists.models.SpotifyPlaylistsResponseBody
import pl.akai.fillist.web.utils.PlaylistUtils

class PlaylistUtilsTests {
    @Test
    fun toPlaylistsNormalResponse() {
        testToPlaylists(
            images = listOf(
                Image(
                    height = 100,
                    url = "url",
                    width = 100,
                ),
                Image(
                    height = 200,
                    url = "url1",
                    width = 200,
                ),
            ),
            validImageUrl = "url1",
            playlistName = "name",
            validPlaylistName = "name",
        )
    }

    @Test
    fun toPlaylistsEmptyImage() {
        testToPlaylists(
            images = listOf(),
            validImageUrl = null,
            playlistName = "name",
            validPlaylistName = "name",
        )
    }

    @Test
    fun toPlaylistEmptyHeightImage() {
        testToPlaylists(
            images = listOf(
                Image(
                    height = null,
                    url = "url",
                    width = null,
                ),
            ),
            validImageUrl = "url",
            playlistName = "name",
            validPlaylistName = "name",
        )
    }

    @Test
    fun toPlaylistsTooLongName() {
        testToPlaylists(
            images = listOf(
                Image(
                    height = 100,
                    url = "url",
                    width = 100,
                ),
            ),
            validImageUrl = "url",
            playlistName = "name1 name1 name1 name1 name1 name1",
            validPlaylistName = "name1 name1 name1 name1...",
        )
    }

    @Test
    fun toPlaylistsTooLongOneName() {
        testToPlaylists(
            images = listOf(
                Image(
                    height = 100,
                    url = "url",
                    width = 100,
                ),
            ),
            validImageUrl = "url",
            playlistName = "name1name1name1name1name1name111",
            validPlaylistName = "name1name1name1name1name1na...",
        )
    }

    private fun testToPlaylists(
        images: List<Image>,
        validImageUrl: String?,
        playlistName: String,
        validPlaylistName: String,
    ) {
        val spotifyPlaylistsResponseBody = SpotifyPlaylistsResponseBody(
            total = 1,
            limit = 1,
            offset = 1,
            items = listOf(
                SpotifyPlaylist(
                    description = "description",
                    externalUrls = ExternalUrls(
                        spotify = "spotify",
                    ),
                    id = "id",
                    images = images,
                    name = playlistName,
                    owner = Owner(
                        externalUrls = ExternalUrls(
                            spotify = "spotify",
                        ),
                        displayName = "displayName",
                        id = "id",
                    ),
                ),
            ),
        )
        val response = PlaylistUtils.toPlaylists(spotifyPlaylistsResponseBody)
        val validResponse = PlaylistsResponseBody(
            total = 1,
            limit = 1,
            offset = 1,
            playlists = listOf(
                PlaylistsResponseBody.Playlist(
                    id = "id",
                    name = validPlaylistName,
                    ownerDisplayName = "displayName",
                    image = validImageUrl,
                ),
            ),
        )
        assertEquals(response.block(), validResponse)
    }
}
