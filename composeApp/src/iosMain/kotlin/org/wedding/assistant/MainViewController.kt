package org.wedding.assistant

import androidx.compose.ui.window.ComposeUIViewController

fun MainViewController() = ComposeUIViewController {
    App(
        onGoogleClick = { IosCallbacks.onGoogleClick?.invoke() }
    )
}