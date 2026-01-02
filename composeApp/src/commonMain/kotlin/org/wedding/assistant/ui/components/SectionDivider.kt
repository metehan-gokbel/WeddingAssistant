package org.wedding.assistant.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.wedding.assistant.ui.theme.WeddingColors

@Composable
fun CenterDividerText(text: String) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Divider(Modifier.weight(1f), color = WeddingColors.White.copy(alpha = 0.18f))
        Text("  $text  ", color = WeddingColors.White40, style = MaterialTheme.typography.labelSmall)
        Divider(Modifier.weight(1f), color = WeddingColors.White.copy(alpha = 0.18f))
    }
}