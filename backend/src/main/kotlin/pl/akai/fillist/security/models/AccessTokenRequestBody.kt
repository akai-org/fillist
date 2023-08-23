package pl.akai.fillist.security.models

import kotlinx.serialization.Serializable
import org.springframework.util.LinkedMultiValueMap

@Serializable
data class AccessTokenRequestBody(
    val grantType: String,
    val code: String,
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
