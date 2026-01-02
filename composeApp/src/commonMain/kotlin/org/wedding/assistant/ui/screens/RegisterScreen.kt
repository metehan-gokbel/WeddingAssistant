package org.wedding.assistant.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Female
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Male
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.wedding.assistant.auth.domain.WeddingRole
import org.wedding.assistant.ui.components.AuthBackground
import org.wedding.assistant.ui.components.WeddingTextField
import org.wedding.assistant.ui.theme.WeddingColors
import weddingassistant.composeapp.generated.resources.Res
import weddingassistant.composeapp.generated.resources.ic_google
import weddingassistant.composeapp.generated.resources.ic_heart

@Composable
fun RegisterScreen(
    role: WeddingRole,
    fullName: String,
    partnerName: String,
    weddingDateText: String,
    email: String,
    password: String,
    passwordRepeat: String,
    termsAccepted: Boolean,
    loading: Boolean,
    error: String?,
    onRoleChange: (WeddingRole) -> Unit,
    onFullNameChange: (String) -> Unit,
    onPartnerNameChange: (String) -> Unit,
    onWeddingDateClick: () -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPasswordRepeatChange: (String) -> Unit,
    onTermsAcceptedChange: (Boolean) -> Unit,
    onRegister: () -> Unit,
    onGoogleRegister: () -> Unit,
    onGoToLogin: () -> Unit,
) {
    var showPass by remember { mutableStateOf(false) }

    AuthBackground {
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

            Spacer(Modifier.height(10.dp))

            Text(
                text = "Hesap Oluştur",
                color = WeddingColors.White,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )

            Spacer(Modifier.height(6.dp))

            Text(
                text = "Hayalindeki düğün için ilk adımı at.",
                color = WeddingColors.TextMuted,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(Modifier.height(22.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 420.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = "Rolünüz",
                color = WeddingColors.White75
            )

            Spacer(Modifier.height(4.dp))

            RoleSelector(
                role = role,
                onRoleChange = onRoleChange
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = "Ad Soyad",
                color = WeddingColors.White75
            )
            Spacer(Modifier.height(4.dp))
            WeddingTextField(
                value = fullName,
                onValueChange = onFullNameChange,
                placeholder = "Adınız Soyadınız",
                leadingIcon = { Icon(Icons.Outlined.Person, contentDescription = null) }
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = "E-posta",
                color = WeddingColors.White75
            )
            Spacer(Modifier.height(4.dp))
            WeddingTextField(
                value = email,
                onValueChange = onEmailChange,
                placeholder = "name@example.com",
                leadingIcon = { Icon(Icons.Outlined.Email, contentDescription = null) }
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = "Partnerin Adı",
                color = WeddingColors.White75
            )
            Spacer(Modifier.height(4.dp))
            WeddingTextField(
                value = partnerName,
                onValueChange = onPartnerNameChange,
                placeholder = "Partnerinizin Adı",
                leadingIcon = { Icon(Icons.Outlined.FavoriteBorder, contentDescription = null) }
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = "Düğün Tarihi",
                color = WeddingColors.White75
            )
            Spacer(Modifier.height(4.dp))
            WeddingTextField(
                value = weddingDateText,
                onValueChange = {},
                placeholder = "Tarih seç",
                readOnly = true,
                onClick = onWeddingDateClick,
                leadingIcon = { Icon(Icons.Outlined.DateRange, contentDescription = null) },
                trailingIcon = { Icon(Icons.Outlined.ChevronRight, contentDescription = null) }
            )

            Spacer(Modifier.height(12.dp))

            Text("Şifre", color = WeddingColors.White75)
            Spacer(Modifier.height(4.dp))
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

            Spacer(Modifier.height(12.dp))

            // Şifre tekrar
            Text("Şifre Tekrar", color = WeddingColors.White75)
            Spacer(Modifier.height(4.dp))
            WeddingTextField(
                value = passwordRepeat,
                onValueChange = onPasswordRepeatChange,
                placeholder = "Şifre giriniz",
                leadingIcon = { Icon(Icons.Outlined.Lock, contentDescription = null) },
                visualTransformation = PasswordVisualTransformation(),
            )

            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Checkbox(
                    checked = termsAccepted,
                    onCheckedChange = onTermsAcceptedChange,
                    colors = CheckboxDefaults.colors(
                        checkedColor = WeddingColors.Primary,
                        uncheckedColor = WeddingColors.Border,
                        checkmarkColor = WeddingColors.White
                    )
                )
                Spacer(Modifier.width(10.dp))
                Text(
                    text = "Kullanım şartlarını ve gizlilik politikasını okudum, kabul ediyorum.",
                    color = WeddingColors.TextMuted,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }

            if (!error.isNullOrBlank()) {
                Spacer(Modifier.height(10.dp))
                Text(error, color = Color(0xFFFF6B6B))
            }

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = onRegister,
                enabled = !loading && termsAccepted,
                modifier = Modifier.fillMaxWidth().height(54.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = WeddingColors.Primary,
                    contentColor = WeddingColors.White,
                    disabledContainerColor = WeddingColors.SurfaceHover.copy(alpha = 0.95f),
                    disabledContentColor = WeddingColors.TextMuted
                ),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text(if (loading) "Kayıt Olunuyor" else "Kayıt Ol", color = WeddingColors.White)
            }

            Spacer(Modifier.height(22.dp))

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
                onClick = onGoogleRegister,
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
                Text("Google ile Kayıt Ol", color = WeddingColors.White)
            }

            Spacer(Modifier.height(16.dp))

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Zaten hesabın var mı?", color = WeddingColors.TextMuted)
                Spacer(Modifier.width(6.dp))
                TextButton(onClick = onGoToLogin, contentPadding = PaddingValues(0.dp)) {
                    Text("Giriş Yap", color = WeddingColors.Primary)
                }
            }
        }
    }
}

@Composable
fun RoleSelector(
    role: WeddingRole,
    onRoleChange: (WeddingRole) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .background(WeddingColors.Surface.copy(alpha = 0.92f), RoundedCornerShape(14.dp))
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        RoleChip(
            selected = role == WeddingRole.Bride,
            text = "Gelin",
            icon = Icons.Outlined.Female,
            onClick = { onRoleChange(WeddingRole.Bride) },
            modifier = Modifier.weight(1f)
        )

        RoleChip(
            selected = role == WeddingRole.Groom,
            text = "Damat",
            icon = Icons.Outlined.Male,
            onClick = { onRoleChange(WeddingRole.Groom) },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun RoleChip(
    selected: Boolean,
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bg = if (selected) WeddingColors.Primary else Color.Transparent
    val fg = if (selected) WeddingColors.White else WeddingColors.White60

    Row(
        modifier = modifier
            .fillMaxHeight()
            .background(bg, RoundedCornerShape(10.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = fg,
            modifier = Modifier.size(18.dp)
        )

        Spacer(Modifier.width(8.dp))

        Text(
            text = text,
            color = fg,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}