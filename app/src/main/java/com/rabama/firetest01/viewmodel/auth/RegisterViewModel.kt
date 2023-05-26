package com.rabama.firetest01.viewmodel.auth

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.auth.FirebaseAuth

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    fun registerUser(email: String, password: String, callback: (Boolean) -> Unit) {
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true)
                } else {
                    callback(false)
                }
            }
    }
}