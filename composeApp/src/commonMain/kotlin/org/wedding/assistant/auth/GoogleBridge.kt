package org.wedding.assistant.auth

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

data class GoogleToken(val idToken: String, val accessToken: String? = null)

private val _googleTokens = MutableSharedFlow<GoogleToken>(extraBufferCapacity = 1)
val googleTokens: SharedFlow<GoogleToken> = _googleTokens

private val _googleErrors = MutableSharedFlow<String>(extraBufferCapacity = 1)
val googleErrors: SharedFlow<String> = _googleErrors

// Swift burayı çağıracak
fun publishGoogleToken(idToken: String, accessToken: String? = null) {
    _googleTokens.tryEmit(GoogleToken(idToken, accessToken))
}

fun publishGoogleError(message: String) {
    _googleErrors.tryEmit(message)
}