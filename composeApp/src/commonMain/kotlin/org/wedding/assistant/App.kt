package org.wedding.assistant

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.wedding.assistant.auth.data.provideAuthRepository
import org.wedding.assistant.auth.domain.WeddingRole
import org.wedding.assistant.auth.googleErrors
import org.wedding.assistant.auth.googleTokens
import org.wedding.assistant.auth.presentation.LoginViewModel
import org.wedding.assistant.auth.presentation.RegisterViewModel
import org.wedding.assistant.navigation.AuthRoute
import org.wedding.assistant.platform.rememberDatePickerLauncher
import org.wedding.assistant.ui.screens.HomeScreen
import org.wedding.assistant.ui.screens.LoginScreen
import org.wedding.assistant.ui.screens.RegisterScreen
import org.wedding.assistant.ui.theme.rememberAppTypography

@Composable
@Preview
fun App(
    onGoogleClick: () -> Unit = {}
) {
    val repo = remember { provideAuthRepository() }
    val loginVm = remember { LoginViewModel(repo) }
    val loginState by loginVm.state.collectAsState()

    val registerVm = remember { RegisterViewModel(repo) }
    val reg by registerVm.state.collectAsState()

    // kullanıcı giriş yaptıysa burada route değiştirirsin
    var route by remember { mutableStateOf(AuthRoute.Login) }

    LaunchedEffect(Unit) {
        googleTokens.collect { tok ->
            loginVm.signInWithGoogle(tok.idToken, tok.accessToken) {
                route = AuthRoute.Home
            }
        }
    }

    LaunchedEffect(Unit) {
        googleErrors.collect { msg ->
            println("Google error: $msg")
            // istersen state'e de bas
        }
    }

    WeddingTheme {
        when (route) {
            AuthRoute.Login -> {
                LoginScreen(
                    email = loginState.email,
                    password = loginState.password,
                    loading = loginState.isLoading,
                    error = loginState.error,
                    onEmailChange = loginVm::onEmailChange,
                    onPasswordChange = loginVm::onPasswordChange,
                    onSignIn = loginVm::signIn,
                    onForgotPassword = { /* sonra */ },
                    onGoogleSignIn = { onGoogleClick() },
                    onCreateAccount = { route = AuthRoute.Register }
                )
            }

            AuthRoute.Register -> {
                val openDatePicker = rememberDatePickerLauncher(2026, 1, 1) { y, m, d ->
                    fun two(n: Int) = if (n < 10) "0$n" else "$n"
                    registerVm.onWeddingDateChange("${two(d)}.${two(m)}.$y")
                }

                RegisterScreen(
                    role = if (reg.role.name == "Bride") WeddingRole.Bride else WeddingRole.Groom,
                    fullName = reg.fullName,
                    partnerName = reg.partnerName,
                    weddingDateText = reg.weddingDate,
                    email = reg.email,
                    password = reg.password,
                    passwordRepeat = reg.passwordRepeat,
                    termsAccepted = reg.termsAccepted,
                    loading = reg.isLoading,
                    error = reg.error,

                    onRoleChange = { uiRole ->
                        registerVm.onRoleChange(if (uiRole == WeddingRole.Bride) WeddingRole.Bride else WeddingRole.Groom)
                    },
                    onFullNameChange = registerVm::onFullNameChange,
                    onPartnerNameChange = registerVm::onPartnerNameChange,
                    onWeddingDateClick = openDatePicker,
                    onEmailChange = registerVm::onEmailChange,
                    onPasswordChange = registerVm::onPasswordChange,
                    onPasswordRepeatChange = registerVm::onPasswordRepeatChange,
                    onTermsAcceptedChange = registerVm::onTermsChange,

                    onRegister = { registerVm.signUp {
                        route = AuthRoute.Home
                    } },
                    onGoogleRegister = { onGoogleClick() },
                    onGoToLogin = {
                        route = AuthRoute.Login
                    }
                )
            }

            AuthRoute.Home -> {
                HomeScreen()
            }
        }
    }
}

@Composable
fun WeddingTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        typography = rememberAppTypography(),
        content = content
    )
}
