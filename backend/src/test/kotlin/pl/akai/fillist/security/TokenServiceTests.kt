package pl.akai.fillist.security

import AccessTokenResponseBody
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import pl.akai.fillist.security.service.TokenService
import pl.akai.fillist.web.spotifywrapper.models.Image
import pl.akai.fillist.web.spotifywrapper.user.SpotifyProfileResponseBody
import pl.akai.fillist.web.spotifywrapper.user.SpotifyUserService
import reactor.core.publisher.Mono

@ExtendWith(SpringExtension::class)
@SpringBootTest
class TokenServiceTests {

    @Autowired
    private lateinit var tokenService: TokenService

    @MockBean
    lateinit var spotifyUserService: SpotifyUserService

    var spotifyToken: String = "spotifyToken"

    @BeforeEach
    fun setUp() {
        configMock()
    }

    fun configMock() {
        `when`(spotifyUserService.getProfile(spotifyToken)).thenReturn(
            Mono.just(
                SpotifyProfileResponseBody(
                    "id",
                    "email",
                    "displayName",
                    listOf(
                        Image("url", 100, 100),
                    ),
                ),
            ),
        )
    }

    @Test
    fun generateTokenResponse() {
        val token = tokenService.generateTokensResponse(
            AccessTokenResponseBody(
                accessToken = spotifyToken,
                expiresIn = 100,
                tokenType = "",
                refreshToken = "refresh",
            ),
        ).block()!!
        assert(tokenService.getSpotifyAccessToken(token.accessToken) == spotifyToken)
        assert(tokenService.getSpotifyRefreshToken(token.refreshToken) == "refresh")
    }

    @Test
    fun validateToken() {
        val token = tokenService.generateFillistAccessToken(
            AccessTokenResponseBody(
                accessToken = spotifyToken,
                expiresIn = 100,
                tokenType = "",
                refreshToken = "",
            ),
        ).block()!!
        assert(tokenService.validateToken(token))
    }

    @Test
    fun validateTokenInvalid() {
        assert(!tokenService.validateToken("invalid"))
    }

    @Test
    fun generateToken() {
        val token = tokenService.generateFillistAccessToken(
            AccessTokenResponseBody(
                accessToken = spotifyToken,
                expiresIn = 100,
                tokenType = "",
                refreshToken = "",
            ),
        ).block()!!
        assert(token.isNotEmpty())
    }

    @Test
    fun getSpotifyToken() {
        val token = tokenService.generateFillistAccessToken(
            AccessTokenResponseBody(
                accessToken = spotifyToken,
                expiresIn = 100,
                tokenType = "",
                refreshToken = "",
            ),
        ).block()!!
        assert(tokenService.getSpotifyAccessToken(token) == spotifyToken)
    }

    @Test
    fun generateRefreshToken() {
        val token = tokenService.generateFillistRefreshToken(
            AccessTokenResponseBody(
                accessToken = spotifyToken,
                expiresIn = 100,
                tokenType = "",
                refreshToken = "refresh",
            ),
        ).block()!!
        assert(token.isNotEmpty())
    }

    @Test
    fun getRefreshToken() {
        val token = tokenService.generateFillistRefreshToken(
            AccessTokenResponseBody(
                accessToken = spotifyToken,
                expiresIn = 100,
                tokenType = "",
                refreshToken = "refresh",
            ),
        ).block()!!
        assert(tokenService.getSpotifyRefreshToken(token) == "refresh")
    }

    @Test
    fun getSpotifyUserId() {
        val token = tokenService.generateFillistAccessToken(
            AccessTokenResponseBody(
                accessToken = spotifyToken,
                expiresIn = 100,
                tokenType = "",
                refreshToken = "",
            ),
        ).block()!!
        assert(tokenService.getSpotifyUserId(token) == "id")
    }
}
