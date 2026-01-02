package org.wedding.assistant.auth.data

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.GoogleAuthProvider
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.FieldValue.Companion.serverTimestamp
import dev.gitlive.firebase.firestore.firestore
import org.wedding.assistant.auth.domain.AuthRepository

class FirebaseAuthRepository : AuthRepository {
    private val auth = Firebase.auth
    private val db = Firebase.firestore

    override suspend fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
    }

    override suspend fun signUp(data: RegisterData) {
        // 1) Auth user oluştur
        val result = auth.createUserWithEmailAndPassword(data.email, data.password)
        val uid = result.user?.uid ?: error("User uid is null")

        // 2) Firestore'a profile yaz
        val doc = db.collection("users").document(uid)

        val payload = mapOf(
            "uid" to uid,
            "email" to data.email,
            "fullName" to data.fullName,
            "partnerName" to data.partnerName,
            "role" to data.role.name,        // "Bride"/"Groom"
            "weddingDate" to data.weddingDate,
            "createdAt" to serverTimestamp
        )

        doc.set(payload)
    }

    override suspend fun signInWithGoogle(idToken: String, accessToken: String?) {
        val credential = GoogleAuthProvider.credential(idToken = idToken, accessToken = accessToken)
        auth.signInWithCredential(credential)
    }

    override suspend fun signOut() = auth.signOut()

    override fun currentUserId(): String? = auth.currentUser?.uid
}
