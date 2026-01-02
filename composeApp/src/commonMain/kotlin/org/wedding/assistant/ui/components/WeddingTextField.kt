package org.wedding.assistant.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import org.wedding.assistant.ui.theme.WeddingColors

@Composable
fun WeddingTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    readOnly: Boolean = false,
    onClick: (() -> Unit)? = null,
) {
    Box(modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            readOnly = readOnly,
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            placeholder = { Text(placeholder, color = WeddingColors.White.copy(alpha = 0.25f)) },
            visualTransformation = visualTransformation,
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = WeddingColors.Primary,
                unfocusedBorderColor = WeddingColors.Border,
                focusedContainerColor = WeddingColors.Surface.copy(alpha = 0.92f),
                unfocusedContainerColor = WeddingColors.Surface.copy(alpha = 0.92f),
                focusedTextColor = WeddingColors.White,
                unfocusedTextColor = WeddingColors.White,
                focusedLeadingIconColor = WeddingColors.White60,
                unfocusedLeadingIconColor = WeddingColors.White40,
                focusedTrailingIconColor = WeddingColors.White60,
                unfocusedTrailingIconColor = WeddingColors.White40,
            )
        )

        if (readOnly && onClick != null) {
            Box(
                Modifier
                    .matchParentSize()
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) { onClick() }
            )
        }
    }
}