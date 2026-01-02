package org.wedding.assistant.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.wedding.assistant.ui.components.AuthBackground
import org.wedding.assistant.ui.components.WeddingTextField
import org.wedding.assistant.ui.theme.WeddingColors
import weddingassistant.composeapp.generated.resources.Res
import weddingassistant.composeapp.generated.resources.ic_google
import weddingassistant.composeapp.generated.resources.ic_heart

@Composable
fun LoginScreen(
    email: String,
    password: String,
    loading: Boolean,
    error: String?,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignIn: () -> Unit,
    onForgotPassword: () -> Unit,
    onGoogleSignIn: () -> Unit,
    onCreateAccount: () -> Unit,
) {
    var showPass by remember { mutableStateOf(false) }

    AuthBackground {
        // Header
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(Res.drawable.ic_heart),
                    contentDescription = null,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(Modifier.width(10.dp))
                Text(
                    text = "Düğün Asistanım",
                    color = WeddingColors.White,
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.SemiBold)
                )
            }
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Hayalindeki düğünü planlamaya devam etmek için giriş yap.",
                color = WeddingColors.TextMuted,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(Modifier.weight(1f))

        // Form
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 420.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text("E-posta", color = WeddingColors.White75)
            Spacer(Modifier.height(6.dp))

            WeddingTextField(
                value = email,
                onValueChange = onEmailChange,
                placeholder = "name@example.com",
                leadingIcon = { Icon(Icons.Outlined.Email, contentDescription = null) }
            )

            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Şifre", color = WeddingColors.White75)
                TextButton(onClick = onForgotPassword, contentPadding = PaddingValues(0.dp)) {
                    Text("Şifremi unuttum", color = WeddingColors.Primary)
                }
            }

            Spacer(Modifier.height(8.dp))

            WeddingTextField(
                value = password,
                onValueChange = onPasswordChange,
                placeholder = "Şifre giriniz",
                leadingIcon = { Icon(Icons.Outlined.Lock, contentDescription = null) },
                visualTransformation = if (showPass) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { showPass = !showPass }) {
                        Icon(
                            imageVector = if (showPass) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                            contentDescription = null
                        )
                    }
                }
            )

            if (!error.isNullOrBlank()) {
                Spacer(Modifier.height(10.dp))
                Text(error, color = Color(0xFFFF6B6B))
            }

            Spacer(Modifier.height(26.dp))

            Button(
                onClick = onSignIn,
                enabled = !loading,
                modifier = Modifier.fillMaxWidth().height(54.dp),
                colors = ButtonDefaults.buttonColors(containerColor = WeddingColors.Primary),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text(if (loading) "Giriş Yapılıyor" else "Giriş Yap", color = WeddingColors.White)
            }

            Spacer(Modifier.height(22.dp))

            // Divider text
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Divider(Modifier.weight(1f), color = WeddingColors.White.copy(alpha = 0.18f))
                Text(
                    "  VEYA  ",
                    color = WeddingColors.TextMuted,
                    style = MaterialTheme.typography.labelSmall
                )
                Divider(Modifier.weight(1f), color = WeddingColors.White.copy(alpha = 0.18f))
            }

            Spacer(Modifier.height(18.dp))

            OutlinedButton(
                onClick = onGoogleSignIn,
                modifier = Modifier.fillMaxWidth().height(54.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = WeddingColors.Surface.copy(alpha = 0.92f)
                ),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    width = 1.dp,
                    brush = Brush.linearGradient(listOf(WeddingColors.Border, WeddingColors.Border))
                )
            ) {
                Image(
                    painter = painterResource(Res.drawable.ic_google),
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(Modifier.width(10.dp))
                Text("Google ile Giriş Yap", color = WeddingColors.White)
            }

            Spacer(Modifier.height(16.dp))

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Hesabın yok mu?", color = WeddingColors.TextMuted)
                Spacer(Modifier.width(6.dp))
                TextButton(onClick = onCreateAccount, contentPadding = PaddingValues(0.dp)) {
                    Text("Hesap oluştur", color = WeddingColors.Primary)
                }
            }
        }

        Spacer(Modifier.height(10.dp))

        Text(
            "\"Kusursuz düğün, kusursuz bir planla başlar. Siz bu yolculuğun tadını çıkarırken detayları bize bırakın..\"",
            color = WeddingColors.White.copy(alpha = 0.30f),
            fontStyle = FontStyle.Italic,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
        )
    }
}