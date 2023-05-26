package com.rabama.firetest01.adapters


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rabama.firetest01.R
import com.rabama.firetest01.ui.news.DetailActivity
import com.rabama.firetest01.ui.news.fragments.TopNewsFragment
import com.rabama.firetest01.database.entities.Article


// Rellena el RecyclerView con artículos de noticias
class NewsAdapter(private val newsList: List<Article>, private val context: Fragment) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    // Crea un nuevo ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_news, parent, false)
        return NewsViewHolder(itemView)
    }

    // Bindea cada noticia con su ViewHolder
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem = newsList[position]
        holder.bind(currentItem)

        // Lanza DetailActivity con los detalles del artículo de noticias seleccionado
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java).apply {
                putExtra("title", currentItem.title)
                putExtra("description", currentItem.description)
                putExtra("imageUrl", currentItem.urlToImage)
                putExtra("url", currentItem.url)
            }
            holder.itemView.context.startActivity(intent)
        }
    }

    // Devuelve el número total de la lista de artículos
    override fun getItemCount() = newsList.size

    // Contiene la vista de cada artículo
    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val newsTitleTextView: TextView = itemView.findViewById(R.id.newsTitleTextView)
        private val newsImageView: ImageView = itemView.findViewById(R.id.newsImageView)

        // Bindea los datos del artículo a la vista
        fun bind(article: Article) {
            newsTitleTextView.text = article.title
            Glide.with(newsImageView.context)
                .load(article.urlToImage)
                .placeholder(R.drawable.img_placeholder)
                .into(newsImageView)
        }
    }
}