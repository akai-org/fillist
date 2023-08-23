package pl.akai.fillist.security.models

import kotlinx.serialization.Serializable
import org.springframework.util.LinkedMultiValueMap

@Serializable
data class RefreshTokenRequestBody(
    private val grantType: String,
    private val refreshToken: String
){
    fun toLinkedMultiValueMap(): LinkedMultiValueMap<String, String> {
        return LinkedMultiValueMap<String, String>().apply {
            add("grant_type", grantType)
            add("refresh_token", refreshToken)
        }
    }
}
