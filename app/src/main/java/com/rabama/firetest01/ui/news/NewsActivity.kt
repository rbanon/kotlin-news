package com.rabama.firetest01.ui.news

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.firebase.auth.FirebaseAuth
import com.rabama.firetest01.R
import com.rabama.firetest01.databinding.ActivityNewsBinding
import com.rabama.firetest01.ui.auth.AuthActivity
import com.rabama.firetest01.ui.settings.SettingsActivity

enum class ProviderType {
    BASIC,
    GOOGLE
}

class NewsActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener  {

    private lateinit var binding: ActivityNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup
        val bundle = intent.extras
        val email = bundle?.getString("email") ?: ""
        val provider = bundle?.getString("provider") ?: ""

        // Guardado de datos
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.putString("email", email)
        prefs.putString("provider", provider)
        prefs.apply()

        val navController =
            (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?)!!.navController

        NavigationUI.setupWithNavController(binding.bottomNavView, navController)

        // Establecer el tema
        setTheme()

        // SharedPreferences
        androidx.preference.PreferenceManager.getDefaultSharedPreferences(this)
            .registerOnSharedPreferenceChangeListener(this)

    }

    // Inflar el menú de opciones
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    // SharedPreferences
    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == "theme") {
            setTheme()
        }
    }

    // Establecer el tema por SharedPreferences
    private fun setTheme() {
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val theme = if (prefs.getBoolean("theme", false)) {
            Log.d("NewsActivity", "Dark theme")
            AppCompatDelegate.MODE_NIGHT_YES
        } else {
            Log.d("NewsActivity", "Light theme")
            AppCompatDelegate.MODE_NIGHT_NO
        }
        AppCompatDelegate.setDefaultNightMode(theme)
    }

    // Manejar los clicks en el menú de opciones
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.menu_settings -> {
                // Abrir la actividad de configuración
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.menu_logout -> {
                // Cerrar sesión
                val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
                prefs.clear()
                prefs.apply()
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this, AuthActivity::class.java))
                return true
            }

            R.id.menu_exit -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)

    }

    override fun finish() {
        AlertDialog.Builder(this)
            .setTitle("¿Seguro que desea salir?")
            .setPositiveButton("Sí") { _, _ ->
                super.finish()
            }
            .setNegativeButton("No", null)
            .show()
        }

}