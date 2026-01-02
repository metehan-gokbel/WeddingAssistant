package org.wedding.assistant.auth.domain

import org.wedding.assistant.auth.data.RegisterData

interface AuthRepository {
    suspend fun signIn(email: String, password: String)
    suspend fun signUp(data: RegisterData)
    suspend fun signInWithGoogle(idToken: String, accessToken: String? = null)
    suspend fun signOut()
    fun currentUserId(): String?
}