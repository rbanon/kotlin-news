package com.rabama.firetest01.utils
import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar


class Utils {

    private lateinit var snackbar: Snackbar

    fun hideKeyboard(context: Context, view: View) {
        val inputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun showAlert(context: Context, title:String, desc: String ) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(desc)
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    fun showSnackbar(view: View, message: String) {
        snackbar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE)
        snackbar.setAction("Aceptar") {
            snackbar.dismiss()
        }
        snackbar.show()
    }
}
