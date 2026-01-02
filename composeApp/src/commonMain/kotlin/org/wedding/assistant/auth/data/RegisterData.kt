package org.wedding.assistant.auth.data

import org.wedding.assistant.auth.domain.WeddingRole

data class RegisterData(
    val email: String,
    val password: String,
    val fullName: String,
    val partnerName: String,
    val role: WeddingRole,
    val weddingDate: String
)