package pl.akai.fillist.security.service

import AccessTokenResponseBody
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTCreationException
import com.auth0.jwt.exceptions.JWTVerificationException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import pl.akai.fillist.web.spotifywrapper.SpotifyWrapperService
import reactor.core.publisher.Mono
import java.util.*

@Service
class TokenService @Autowired constructor(
    private val spotifyWrapperService: SpotifyWrapperService,
) {

    companion object {
        const val ISSUER = "fillist"
        const val EMAIL_KEY = "email"
        const val ACCESS_TOKEN_KEY = "access_token"
        const val REFRESH_TOKEN_KEY = "refresh_token"
        val LOGGER: Logger = LoggerFactory.getLogger(TokenService::class.java)
    }

    @Value("\${fillist.oauth2.jwt-secret}")
    private val secret: String? = null

    fun generateTokensResponse(token: AccessTokenResponseBody): Mono<AccessTokenResponseBody> {
        return generateFillistAccessToken(token).zipWith(generateFillistRefreshToken(token)).map {
            AccessTokenResponseBody(it.t1, token.tokenType, token.expiresIn, it.t2)
        }
    }

    fun generateFillistAccessToken(token: AccessTokenResponseBody): Mono<String> {
        return this.spotifyWrapperService.user.getProfile(token.accessToken).handle { it, sink ->
            try {
                sink.next(
                    JWT.create()
                        .withIssuer(ISSUER)
                        .withClaim(EMAIL_KEY, it.email)
                        .withExpiresAt(expireAt(token.expiresIn))
                        .withClaim(ACCESS_TOKEN_KEY, token.accessToken)
                        .sign(algorithm()),
                )
            } catch (exception: JWTCreationException) {
                LOGGER.warn("JWT creation exception:", exception)
                sink.error(exception)
            }
        }
    }

    fun generateFillistRefreshToken(token: AccessTokenResponseBody): Mono<String> {
        return try {
            Mono.just(
                JWT.create()
                    .withIssuer(ISSUER)
                    .withClaim(REFRESH_TOKEN_KEY, token.refreshToken)
                    .sign(algorithm()),
            )
        } catch (exception: JWTCreationException) {
            LOGGER.warn("JWT creation exception:", exception)
            Mono.error(exception)
        }
    }

    fun getSpotifyAccessToken(token: String): String {
        try {
            val verifier: JWTVerifier = JWT.require(algorithm())
                .withIssuer(ISSUER)
                .build()

            val decodedJWT = verifier.verify(token)
            return decodedJWT.getClaim(ACCESS_TOKEN_KEY).asString()
        } catch (exception: JWTVerificationException) {
            LOGGER.warn("JWT verification exception:", exception)
            throw exception
        }
    }

    fun getSpotifyEmail(token: String): String {
        try {
            val verifier: JWTVerifier = JWT.require(algorithm())
                .withIssuer(ISSUER)
                .build()

            val decodedJWT = verifier.verify(token)
            return decodedJWT.getClaim(EMAIL_KEY).asString()
        } catch (exception: JWTVerificationException) {
            LOGGER.warn("JWT verification exception:", exception)
            throw exception
        }
    }

    fun getSpotifyRefreshToken(token: String?): String {
        try {
            val verifier: JWTVerifier = JWT.require(algorithm())
                .withIssuer(ISSUER)
                .build()

            val decodedJWT = verifier.verify(token)
            return decodedJWT.getClaim(REFRESH_TOKEN_KEY).asString()
        } catch (exception: JWTCreationException) {
            LOGGER.warn("JWT verification exception:", exception)
            throw exception
        }
    }

    fun validateToken(token: String): Boolean {
        return try {
            val verifier: JWTVerifier = JWT.require(algorithm())
                .withIssuer(ISSUER)
                .build()
            verifier.verify(token)
            true
        } catch (exception: Exception) {
            false
        }
    }

    private fun algorithm(): Algorithm {
        return Algorithm.HMAC256(secret)
    }

    private fun expireAt(expireInSeconds: Int): Date {
        return Date(Date().time + expireInSeconds * 1000)
    }
}
