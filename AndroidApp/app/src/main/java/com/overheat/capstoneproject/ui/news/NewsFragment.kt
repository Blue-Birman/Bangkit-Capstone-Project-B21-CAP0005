package com.overheat.capstoneproject.ui.news

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.overheat.capstoneproject.core.data.Resource
import com.overheat.capstoneproject.core.ui.NewsListAdapter
import com.overheat.capstoneproject.databinding.FragmentNewsBinding
import org.koin.android.viewmodel.ext.android.viewModel

class NewsFragment : Fragment() {

    private val viewModel: NewsViewModel by viewModel()
    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerViewNews()
    }

    private fun prepareRecyclerViewNews() {
        val newsAdapter = NewsListAdapter()
        viewModel.articles().observe(viewLifecycleOwner, { articles ->
            if (articles != null) {
                when(articles) {
                    is Resource.Success -> {
                        binding.progressBarRvNews.visibility = View.GONE
                        newsAdapter.setArticles(articles.data)
                    }
                    is Resource.Loading -> {
                        binding.progressBarRvNews.visibility = View.VISIBLE
                    }
                    is Resource.Error -> {
                        // Error
                        binding.progressBarRvNews.visibility = View.GONE
                    }
                }
            }
        })

        with(binding.rvNews) {
            layoutManager = LinearLayoutManager(activity)
            this.adapter = newsAdapter
        }
    }
}