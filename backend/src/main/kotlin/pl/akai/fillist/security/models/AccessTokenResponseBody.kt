import kotlinx.serialization.Serializable

@Serializable
class AccessTokenResponseBody(
    val accessToken: String,
    val tokenType: String,
    val expiresIn: Int,
    var refreshToken: String? = null,
)
