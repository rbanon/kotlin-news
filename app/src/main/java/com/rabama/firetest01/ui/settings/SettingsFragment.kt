package com.rabama.firetest01.ui.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import com.rabama.firetest01.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        // Inflar el archivo XML de las preferencias
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        // Establecer una referencia a este fragmento
        preferenceFragmentCompat = this
    }

    companion object {
        var preferenceFragmentCompat: PreferenceFragmentCompat? = null
    }
}