package org.wedding.assistant.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.wedding.assistant.ui.theme.WeddingColors
import weddingassistant.composeapp.generated.resources.Res
import weddingassistant.composeapp.generated.resources.ic_google

@Composable
fun GoogleButton(
    text: String,
    onClick: () -> Unit,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().height(54.dp),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.outlinedButtonColors(containerColor = WeddingColors.Surface.copy(alpha = 0.92f)),
        border = ButtonDefaults.outlinedButtonBorder.copy(
            width = 1.dp,
            brush = Brush.linearGradient(listOf(WeddingColors.Border, WeddingColors.Border))
        )
    ) {
        Image(
            painter = painterResource(Res.drawable.ic_google),
            contentDescription = null,
            modifier = Modifier.height(18.dp)
        )
        Spacer(Modifier.width(10.dp))
        Text(text, color = WeddingColors.White)
    }
}