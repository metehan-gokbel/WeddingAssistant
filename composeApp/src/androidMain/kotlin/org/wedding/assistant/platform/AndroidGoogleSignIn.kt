package org.wedding.assistant.platform

import android.app.Activity
import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

private const val WEB_CLIENT_ID =
    "599538577624-oo19usienhf2qroam3aq9cke0glgo55l.apps.googleusercontent.com"

private fun Context.findActivity(): Activity {
    var ctx = this
    while (ctx is android.content.ContextWrapper) {
        if (ctx is Activity) return ctx
        ctx = ctx.baseContext
    }
    error("Activity not found")
}

@Composable
fun rememberAndroidGoogleSignInLauncher(
    onResult: (idToken: String, accessToken: String?) -> Unit,
    onError: (message: String) -> Unit = {}
): () -> Unit {
    val context = LocalContext.current
    val activity = remember { context.findActivity() }

    val gso = remember {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(WEB_CLIENT_ID)
            .build()
    }
    val client = remember { GoogleSignIn.getClient(activity, gso) }

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { res ->
            try {
                val task = GoogleSignIn.getSignedInAccountFromIntent(res.data)
                val account = task.result
                val idToken = account.idToken
                if (idToken.isNullOrBlank()) onError("Google idToken null geldi")
                else onResult(idToken, null)
            } catch (t: Throwable) {
                onError(t.message ?: "Google sign-in failed")
            }
        }

    return { launcher.launch(client.signInIntent) }
}