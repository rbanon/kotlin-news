package com.rabama.firetest01.ui.news.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rabama.firetest01.adapters.NewsAdapter
import com.rabama.firetest01.databinding.FragmentCategoryNewsBinding
import com.rabama.firetest01.viewmodel.news.NewsViewModel
import com.rabama.firetest01.database.entities.Article
import com.rabama.firetest01.utils.Utils

class CategoryNewsFragment : Fragment() {

    private lateinit var binding: FragmentCategoryNewsBinding

    lateinit var adapter: NewsAdapter
    lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: NewsViewModel
    var newsList: MutableList<Article> = mutableListOf()
    var utils = Utils()


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // Obtener el ViewModel
        viewModel = ViewModelProvider(this)[NewsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla el layout para este fragmento
        binding = FragmentCategoryNewsBinding.inflate(inflater, container, false)
        val spinner = binding.spinnerCategory
        activity?.title = "Category"

        // Obtener la referencia de la opción seleccionada en el spinner
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            var selectedCategory = ""

            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedCategory = parent.getItemAtPosition(position) as String
                // Llamada a la api
                viewModel.getArticlesByCategory(selectedCategory)

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
            override fun onNothingSelected(parent: AdapterView<*>) {
                selectedCategory = ""
            }
        }



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        utils.showAlert(requireContext(),"Aviso", "Esta pestaña se encuentra en fase beta y solo está disponible en inglés.")


        // Configurar el RecyclerView
        adapter = NewsAdapter(newsList, this)
        recyclerView = binding.categoryNewsRecyclerView
        recyclerView.setHasFixedSize(true)
        // Obtener la orientación actual de la pantalla
        val orientation = resources.configuration.orientation

        // Establecer el LinearLayoutManager o el GridLayoutManager en función de la orientación
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
        } else {
            recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        }
        recyclerView.adapter = adapter
    }

}