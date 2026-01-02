package org.wedding.assistant.auth.presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.wedding.assistant.auth.data.RegisterData
import org.wedding.assistant.auth.domain.AuthRepository
import org.wedding.assistant.auth.domain.WeddingRole

class RegisterViewModel(private val repo: AuthRepository) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> = _state.asStateFlow()

    fun onRoleChange(v: WeddingRole) = _state.update { it.copy(role = v, error = null) }
    fun onFullNameChange(v: String) = _state.update { it.copy(fullName = v, error = null) }
    fun onPartnerNameChange(v: String) = _state.update { it.copy(partnerName = v, error = null) }
    fun onWeddingDateChange(v: String) = _state.update { it.copy(weddingDate = v, error = null) }
    fun onEmailChange(v: String) = _state.update { it.copy(email = v, error = null) }
    fun onPasswordChange(v: String) = _state.update { it.copy(password = v, error = null) }
    fun onPasswordRepeatChange(v: String) = _state.update { it.copy(passwordRepeat = v, error = null) }
    fun onTermsChange(v: Boolean) = _state.update { it.copy(termsAccepted = v, error = null) }

    fun signUp(onSuccess: () -> Unit) {
        scope.launch {
            val s = state.value

            if (!s.termsAccepted) {
                _state.update { it.copy(error = "Devam etmek için şartları kabul etmelisin.") }
                return@launch
            }
            if (s.password != s.passwordRepeat) {
                _state.update { it.copy(error = "Şifreler uyuşmuyor.") }
                return@launch
            }
            if (s.email.isBlank() || s.password.isBlank()) {
                _state.update { it.copy(error = "Email ve şifre boş olamaz.") }
                return@launch
            }

            _state.update { it.copy(isLoading = true, error = null) }
            try {
                repo.signUp(
                    RegisterData(
                        email = s.email.trim(),
                        password = s.password,
                        fullName = s.fullName.trim(),
                        partnerName = s.partnerName.trim(),
                        role = s.role,
                        weddingDate = s.weddingDate
                    )
                )
                _state.update { it.copy(isLoading = false) }
                onSuccess()
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, error = e.message ?: "Kayıt başarısız") }
            }
        }
    }

    fun clear() { scope.cancel() }
}