package com.rabama.firetest01.ui.news

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.rabama.firetest01.R
import com.rabama.firetest01.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Infla y establece el layout para la actividad
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = "Detalles"
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.back_arrow)
        }

        // Recupera los datos pasados desde NewsAdapter
        val title = intent.getStringExtra("title")
        val description = intent.getStringExtra("description")
        val imageUrl = intent.getStringExtra("imageUrl")
        val url = intent.getStringExtra("url")

        // Asigna los datos a los componentes visuales
        binding.tvTitle.text = title
        binding.tvDescription.text = description

        Glide.with(binding.ivImage)
            .load(imageUrl)
            .placeholder(R.drawable.img_placeholder)
            .centerCrop()
            .into(binding.ivImage)

        // Abre el navegador con la URL de la noticia
        binding.btnMore.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
    }

    // Vuelve a la actividad anterior
    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        if (item.itemId == android.R.id.home) {
            finish()
            true
        } else {
            super.onOptionsItemSelected(item)
        }

}