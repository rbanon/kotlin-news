package com.rabama.firetest01.viewmodel.auth

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.rabama.firetest01.R


class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val context = application
    private val auth = FirebaseAuth.getInstance()

    // Inicio de sesión con Google
    fun signInWithGoogle(): Intent {
        val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.current_web_client_id))
            .requestEmail()
            .build()

        val googleClient = GoogleSignIn.getClient(context, googleConf)
        googleClient.signOut()

        return googleClient.signInIntent
    }

    /** Maneja el resultado obtenido del inicio de sesión con Google
    fun handleGoogleSignInResult(data: Intent?, callback: (success: Boolean, errorMessage: String?) -> Unit) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.getResult(ApiException::class.java)
            if (account != null) {
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                auth.signInWithCredential(credential)
                    .addOnCompleteListener { signInTask ->
                        if (signInTask.isSuccessful) {
                            callback.invoke(true, null)
                        } else {
                            callback.invoke(false, "Se ha producido un error autenticando al usuario")
                        }
                    }
            }
        } catch (e: ApiException) {
            callback.invoke(false, e.message.toString())
        }
    }
    */

    // Inicio de sesión con email y contraseña
    fun loginWithEmailAndPassword(email: String, password: String, callback: (success: Boolean) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { signInTask ->
                callback.invoke(signInTask.isSuccessful)
            }
    }

    fun getCurrentUserEmail(): String {
        return auth.currentUser?.email ?: ""
    }

}