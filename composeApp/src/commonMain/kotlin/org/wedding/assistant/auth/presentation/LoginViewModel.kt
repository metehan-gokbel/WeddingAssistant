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
import org.wedding.assistant.auth.domain.AuthRepository

class LoginViewModel(private val repo: AuthRepository) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    fun onEmailChange(v: String) = _state.update { it.copy(email = v, error = null) }
    fun onPasswordChange(v: String) = _state.update { it.copy(password = v, error = null) }

    fun signIn() = runAuth { repo.signIn(state.value.email.trim(), state.value.password) }

    fun signInWithGoogle(idToken: String, accessToken: String? = null, onSuccess: () -> Unit = {}) =
        runAuth(onSuccess) { repo.signInWithGoogle(idToken, accessToken) }

    private fun runAuth(onSuccess: () -> Unit = {}, block: suspend () -> Unit) {
        scope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                block()
                _state.update { it.copy(isLoading = false) }
                onSuccess()
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, error = e.message ?: "Unknown error") }
            }
        }
    }

    private fun runAuth(block: suspend () -> Unit) {
        scope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                block()
                _state.update { it.copy(isLoading = false) }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, error = e.message ?: "Unknown error") }
            }
        }
    }

    fun clear() { scope.cancel() }
}