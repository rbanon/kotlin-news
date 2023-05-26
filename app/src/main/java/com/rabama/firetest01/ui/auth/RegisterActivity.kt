package com.rabama.firetest01.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.rabama.firetest01.R
import com.rabama.firetest01.databinding.ActivityRegisterBinding
import com.rabama.firetest01.ui.news.NewsActivity
import com.rabama.firetest01.ui.news.ProviderType
import com.rabama.firetest01.utils.Utils
import com.rabama.firetest01.viewmodel.auth.RegisterViewModel


class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var snackbar: Snackbar
    private lateinit var registerViewModel: RegisterViewModel

    private val utils = Utils()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener el ViewModel
        registerViewModel = ViewModelProvider(this)[RegisterViewModel::class.java]
        // Configuración inicial
        setup()
    }

    private fun setup() {
        title = "Register"

        val etEmail = binding.etEmail
        val etPassword = binding.etPassword
        val etConfirmPassword = binding.etConfirmPassword
        val tvEmailError = binding.tvEmailError
        val tvPasswordError = binding.tvPasswordError
        val tvConfirmPasswordError = binding.tvConfirmPasswordError

        val isValidEmail: (String) -> Boolean = { email ->
            val regex = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
            regex.matches(email)
        }

        // Comprobar que el email es válido
        etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val email = s.toString().trim()
                if (etEmail.text.isNotEmpty() && isValidEmail(email)) {
                    tvEmailError.visibility = View.INVISIBLE
                } else {
                    tvEmailError.visibility = View.VISIBLE
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // Flag para comprobar que las contraseñas coinciden
        val checkPassword: () -> Boolean = {
            if (etPassword.text.isNotEmpty() && etConfirmPassword.text.isNotEmpty()) {
                etPassword.text.toString() == etConfirmPassword.text.toString()
            } else {
                false
            }
        }

        // Comprobar que la contraseña es válida
        binding.etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val etPassword = s.toString()
                if (etPassword.length < 6) {
                    tvPasswordError.visibility = View.VISIBLE
                    tvPasswordError.text = getString(R.string.registerPasswordShort)
                } else {
                    tvPasswordError.visibility = View.INVISIBLE
                    tvPasswordError.text = ""
                }
            }
        })

        // Comprobar que las contraseñas coinciden
        binding.etConfirmPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val etConfirmPassword = s.toString()
                val etPassword = etPassword.text.toString()
                if (etConfirmPassword != etPassword) {
                    tvConfirmPasswordError.visibility = View.VISIBLE
                    tvConfirmPasswordError.text = getString(R.string.registerPasswordError)
                } else {
                    tvConfirmPasswordError.visibility = View.INVISIBLE
                    tvConfirmPasswordError.text = ""
                }
            }
        })

        // Botón para volver a la pantalla de login
        binding.btnBack.setOnClickListener {
            finish()
        }

        // Botón para registrar el usuario
        binding.btnSignup.setOnClickListener {
            val email = binding.etEmail.text
            val password = binding.etPassword.text

            if (email.isNotEmpty() && password.isNotEmpty() && checkPassword() && isValidEmail(
                    etEmail.text.toString()
                )) {
                registerViewModel.registerUser(email.toString(), password.toString()) { success ->
                    if (success) {
                        showNews(email.toString(), ProviderType.BASIC)
                    } else {
                        utils.showAlert(this,"Error", "No se ha podido registrar el usuario")
                    }
                }
            } else {
                utils.showSnackbar(binding.root,"Por favor, introduce un email y una contraseña válidos")
            }
        }

        // Ocultar el teclado cuando se hace clic fuera de los campos de texto
        binding.root.setOnClickListener {
            utils.hideKeyboard(this, binding.root)
        }
    }

    // Navegar a la pantalla de noticias
    private fun showNews(email: String, provider: ProviderType) {
        val homeIntent = Intent(this, NewsActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
        startActivity(homeIntent)
    }


}