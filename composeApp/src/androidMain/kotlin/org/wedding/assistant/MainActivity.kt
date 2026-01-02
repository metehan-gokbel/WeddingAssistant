package org.wedding.assistant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.wedding.assistant.auth.publishGoogleError
import org.wedding.assistant.auth.publishGoogleToken
import org.wedding.assistant.platform.rememberAndroidGoogleSignInLauncher

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            val googleLauncher = rememberAndroidGoogleSignInLauncher(
                onResult = { idToken, accessToken ->
                    publishGoogleToken(idToken, accessToken)
                },
                onError = { message ->
                    publishGoogleError(message)
                }
            )

            App(onGoogleClick = googleLauncher)
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}
