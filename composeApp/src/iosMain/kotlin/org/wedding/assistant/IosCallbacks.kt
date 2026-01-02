package org.wedding.assistant

object IosCallbacks {
    var onGoogleClick: (() -> Unit)? = null
}

// Swift burayı çağıracak
fun setIosGoogleClickHandler(handler: () -> Unit) {
    IosCallbacks.onGoogleClick = handler
}