package com.rabama.firetest01.ui.news.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rabama.firetest01.adapters.NewsAdapter
import com.rabama.firetest01.databinding.FragmentKeywordNewsBinding
import com.rabama.firetest01.viewmodel.news.NewsViewModel
import com.rabama.firetest01.database.entities.Article
import com.rabama.firetest01.utils.Utils

class KeywordNewsFragment : Fragment() {

    private lateinit var binding: FragmentKeywordNewsBinding
    private lateinit var adapter: NewsAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: NewsViewModel
    private var newsList: MutableList<Article> = mutableListOf()
    private val utils = Utils()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Obtener el ViewModel
        viewModel = ViewModelProvider(this)[NewsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla el layout para este fragmento
        binding = FragmentKeywordNewsBinding.inflate(inflater, container, false)
        activity?.title = "Keyword"

        binding.btnSearch.setOnClickListener {
            utils.hideKeyboard(requireActivity(), binding.etSearch)
            val keyword = binding.etSearch.text.toString()

            // Obtener la lista de noticias desde el ViewModel
            viewModel.getAllArticles(keyword)

            // Observar la lista de noticias desde el ViewModel y actualizar el RecyclerView
            viewModel.newsListLiveData.observe(viewLifecycleOwner){ list ->
                val oldSize = newsList.size
                newsList.clear()
                newsList.addAll(list)
                val newSize = newsList.size
                with(adapter) {
                    this.notifyItemRangeRemoved(0, oldSize)
                    this.notifyItemRangeInserted(0, newSize)
                }
            }
        }
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar el RecyclerView
        adapter = NewsAdapter(newsList, this)
        recyclerView = binding.keywordNewsRecyclerView
        recyclerView.setHasFixedSize(true)
        // Obtener la orientación actual de la pantalla
        val orientation = resources.configuration.orientation

        // Establecer layout en función de la orientación
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
        } else {
            recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        }
        recyclerView.adapter = adapter
    }
}