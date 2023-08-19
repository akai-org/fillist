package pl.akai.fillist.security.sso.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.springframework.util.LinkedMultiValueMap

@Serializable
data class SpotifyRequestAccessTokenBody(
    @SerialName("grant_type")
    val grantType: String,
    val code: String,
    @SerialName("redirect_uri")
    val redirectUri: String,
) {
    fun toLinkedMultiValueMap(): LinkedMultiValueMap<String, String> {
        val formData: LinkedMultiValueMap<String, String> = LinkedMultiValueMap()
        formData.add("grant_type", grantType)
        formData.add("code", code)
        formData.add("redirect_uri", redirectUri)
        return formData
    }
}
