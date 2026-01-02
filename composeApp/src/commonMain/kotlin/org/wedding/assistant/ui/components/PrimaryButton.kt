package org.wedding.assistant.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.wedding.assistant.ui.theme.WeddingColors

@Composable
fun PrimaryButton(
    text: String,
    loadingText: String,
    loading: Boolean,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        enabled = enabled && !loading,
        modifier = Modifier.fillMaxWidth().height(54.dp),
        colors = ButtonDefaults.buttonColors(containerColor = WeddingColors.Primary),
        shape = RoundedCornerShape(14.dp)
    ) {
        Text(if (loading) loadingText else text, color = WeddingColors.White)
    }
}