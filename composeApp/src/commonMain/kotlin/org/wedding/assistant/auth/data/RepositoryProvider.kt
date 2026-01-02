package org.wedding.assistant.auth.data

import org.wedding.assistant.auth.domain.AuthRepository

expect fun provideAuthRepository(): AuthRepository
