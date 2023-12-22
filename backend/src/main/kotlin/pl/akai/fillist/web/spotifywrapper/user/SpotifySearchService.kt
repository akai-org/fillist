package pl.akai.fillist.web.spotifywrapper.user

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class SpotifySearchService @Autowired constructor(
    private val spotifyClient: WebClient,
) {
    companion object {
        const val SEARCH_ENDPOINT = "/search"
    }

    fun search(query: SpotifySearchQueryFilters): Mono<SpotifySearchResponseBody> {
        if (query.types.isEmpty()) {
            throw IllegalArgumentException("At least one type must be provided")
        }

        val queryString = query.toQueryString()
        val types = query.types.joinToString(",") { it.value }

        val resp = spotifyClient.get()
            .uri {
                it
                    .path(SEARCH_ENDPOINT)
                    .queryParam("q", queryString)
                    .queryParam("type", types)
                    .build()
            }
            .retrieve()

        return resp.bodyToMono(SpotifySearchResponseBody::class.java)
    }
}

enum class SpotifySearchType(val value: String) {
    ALBUM("album"),
    ARTIST("artist"),
    PLAYLIST("playlist"),
    TRACK("track"),
}

data class SpotifySearchQueryFilters(
    val genericFilter: String? = null,
    val album: String? = null,
    val artist: String? = null,
    val track: String? = null,
    val year: Number? = null,
    val upc: String? = null,
    val genre: String? = null, // TODO: Introduce genre enum
    val types: List<SpotifySearchType>,
) {
    fun toQueryString(): String {
        val queryParameters = StringBuilder()

        if (genericFilter != null) {
            queryParameters.append("$genericFilter ")
        }

        if (album != null) {
            queryParameters.append("album:\"$album\" ")
        }

        if (artist != null) {
            queryParameters.append("artist:\"$artist\" ")
        }

        if (track != null) {
            queryParameters.append("track:\"$track\" ")
        }

        if (year != null) {
            queryParameters.append("year:\"$year\" ")
        }

        if (upc != null) {
            queryParameters.append("upc:\"$upc\" ")
        }

        if (genre != null) {
            queryParameters.append("genre:\"$genre\" ")
        }

        return queryParameters.toString().trim()
    }
}
