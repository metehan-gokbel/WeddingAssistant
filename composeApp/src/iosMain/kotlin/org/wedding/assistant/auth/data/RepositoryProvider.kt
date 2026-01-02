package org.wedding.assistant.auth.data

import org.wedding.assistant.auth.domain.AuthRepository

actual fun provideAuthRepository(): AuthRepository = FirebaseAuthRepository()