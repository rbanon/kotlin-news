package com.rabama.firetest01.ui.auth

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.rabama.firetest01.ui.news.ProviderType
import com.rabama.firetest01.R
import com.rabama.firetest01.databinding.ActivityAuthBinding
import com.rabama.firetest01.ui.news.NewsActivity
import com.rabama.firetest01.utils.Utils
import com.rabama.firetest01.viewmodel.auth.AuthViewModel

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private val utils = Utils()
    private lateinit var authViewModel: AuthViewModel

    // Maneja el inicio de sesión con Google
    private val googleSignInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

        val resultCode = result.resultCode
        // Manejar el resultado obtenido
        if (resultCode == Activity.RESULT_OK && result.data != null) {
            val userEmail = authViewModel.getCurrentUserEmail()
            val providerType = ProviderType.GOOGLE
            showNews(userEmail, providerType)
        } else {
            val errorMessage = result.data?.getStringExtra("errorMessage")
            utils.showAlert(this, "Error", errorMessage ?: "Se ha producido un error al iniciar sesión con Google")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_MainTheme)
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener el ViewModel
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        // Configuración inicial
        setup()

        // Comprobar sesión existente
        session()
    }

    override fun onStart() {
        super.onStart()
        binding.llAuth.visibility = View.VISIBLE
    }

    private fun session() {
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email = prefs.getString("email", null)
        val provider = prefs.getString("provider", null)

        if (email != null && provider != null) {
            binding.llAuth.visibility = View.INVISIBLE
            showNews(email, ProviderType.valueOf(provider))
        }

    }

    private fun setup() {
        // Registro
        binding.tvSignup.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // Login con Google
        binding.btnGoogle.setOnClickListener {
            val signInIntent = authViewModel.signInWithGoogle()
            googleSignInLauncher.launch(signInIntent)
        }

        // Login normal
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            // Validar campos de email y contraseña
            if (email.isNotEmpty() && password.isNotEmpty()) {
                authViewModel.loginWithEmailAndPassword(email, password) { success ->
                    if (success) {
                        val userEmail = authViewModel.getCurrentUserEmail()
                        val providerType = ProviderType.BASIC
                        showNews(userEmail, providerType)
                    } else {
                        utils.showAlert(this, "Error", "No se ha podido iniciar sesión")
                    }
                }
            } else {
                utils.showAlert(this, "Error", "Por favor, introduce un email y una contraseña")
            }
        }

        // Ocultar el teclado cuando se hace clic fuera de los campos de texto
        binding.root.setOnClickListener {
            utils.hideKeyboard(this, binding.root)
        }
    }

    // Mostrar las noticias y pasar los datos de email y proveedor a la siguiente actividad
    private fun showNews(email: String, provider: ProviderType) {
        val newsIntent = Intent(this, NewsActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
        startActivity(newsIntent)
    }
}