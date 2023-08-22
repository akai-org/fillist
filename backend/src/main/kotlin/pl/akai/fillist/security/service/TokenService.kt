package pl.akai.fillist.security.service

import SpotifyAccessTokenResponseBody
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTCreationException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*

@Service
class TokenService {

    @Value("\${fillist.oauth2.jwt-secret}")
    private val secret: String? = null

    companion object {
        const val ISSUER = "fillist"
    }

    fun generateFillistAccessToken(token: SpotifyAccessTokenResponseBody): String {
        return JWT.create()
            .withIssuer(ISSUER)
            .withExpiresAt(expireAt(token.expiresIn))
            .withClaim("access_token", token.accessToken)
            .sign(algorithm())
    }

    fun generateFillistRefreshToken(token: SpotifyAccessTokenResponseBody): String {
        return try {
            JWT.create()
                .withIssuer(ISSUER)
                .withClaim("refresh_token", token.refreshToken)
                .sign(algorithm())
        } catch (exception: JWTCreationException) {
            // add logger
            ""
        }
    }

    fun getSpotifyAccessToken(token: String): String {
        val verifier: JWTVerifier = JWT.require(algorithm())
            .withIssuer(ISSUER)
            .build()

        val decodedJWT = verifier.verify(token)
        return decodedJWT.getClaim("access_token").asString()
    }

    fun getSpotifyRefreshToken(token: String): String {
        val verifier: JWTVerifier = JWT.require(algorithm())
            .withIssuer(ISSUER)
            .build()

        val decodedJWT = verifier.verify(token)
        return decodedJWT.getClaim("refresh_token").asString()
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

    fun algorithm(): Algorithm {
        return Algorithm.HMAC256(secret)
    }

    fun expireAt(expireInSeconds: Int): Date {
        return Date(Date().time + expireInSeconds * 1000)
    }
}
