package org.wedding.assistant.platform

import android.app.DatePickerDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun rememberDatePickerLauncher(
    initialYear: Int,
    initialMonth: Int,
    initialDay: Int,
    onSelected: (year: Int, month: Int, day: Int) -> Unit
): () -> Unit {
    val context = LocalContext.current

    return remember(initialYear, initialMonth, initialDay) {
        {
            DatePickerDialog(
                context,
                { _, y, m, d -> onSelected(y, m + 1, d) },
                initialYear,
                initialMonth - 1,
                initialDay
            ).show()
        }
    }
}