package org.wedding.assistant.platform

import androidx.compose.runtime.Composable

@Composable
expect fun rememberDatePickerLauncher(
    initialYear: Int,
    initialMonth: Int,
    initialDay: Int,
    onSelected: (year: Int, month: Int, day: Int) -> Unit
): () -> Unit