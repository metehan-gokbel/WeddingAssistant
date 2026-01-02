package org.wedding.assistant.auth.presentation

import org.wedding.assistant.auth.domain.WeddingRole

data class RegisterState(
    val role: WeddingRole = WeddingRole.Bride,
    val fullName: String = "",
    val partnerName: String = "",
    val weddingDate: String = "",
    val email: String = "",
    val password: String = "",
    val passwordRepeat: String = "",
    val termsAccepted: Boolean = false,

    val isLoading: Boolean = false,
    val error: String? = null,
)
