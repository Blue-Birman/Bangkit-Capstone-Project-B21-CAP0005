package com.overheat.capstoneproject.ui.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.overheat.capstoneproject.R
import com.overheat.capstoneproject.core.data.Resource
import com.overheat.capstoneproject.core.domain.model.Article
import com.overheat.capstoneproject.core.ui.HomeListFaqAdapter
import com.overheat.capstoneproject.databinding.FragmentHomeBinding
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModel()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fillHomeFragment()
        prepareRecyclerView()
        prepareCarouselView()
    }

    private fun fillHomeFragment() {
        with(binding) {
            tvWelcomeUser.text = context?.resources?.getString(R.string.welcome_guest, "Guest")
            tvWelcomeText.text = "lorem ipsum dolor sit amet"
        }
    }

    private fun prepareRecyclerView() {
        val adapter = HomeListFaqAdapter()
        viewModel.faqs().observe(viewLifecycleOwner, { listFaqs ->
            if (listFaqs != null) {
                when(listFaqs) {
                    is Resource.Success -> {
                        adapter.setDataFaqs(listFaqs.data)
                    }
                    is Resource.Loading -> {
                        // Loading
                    }
                    is Resource.Error -> {
                        // Error
                    }
                }
            }
        })

        with(binding.rvFaqs) {
            this.layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }
    }

    private fun prepareCarouselView() {
        val listArticle = ArrayList<Article>()
        viewModel.articles().observe(viewLifecycleOwner, { articles ->
            if (articles != null) {
                when(articles) {
                    is Resource.Success -> {
                        articles.data?.let { listArticle.addAll(it) }
                    }
                    is Resource.Loading -> {
                        // Loading
                    }
                    is Resource.Error -> {
                        // Error
                    }
                }
            }
        })

        with(binding.carouselView) {
            // Set image listener must be called before page count
            // If not, it will cause error -> View must set ImageListener or ViewListener
            setImageListener { position, imageView ->
                if (listArticle[position].image != null) {
                    Glide.with(this@HomeFragment)
                        .load(listArticle[position].image)
                        .into(imageView)
                } else {
                    Glide.with(this@HomeFragment)
                        .load(R.drawable.no_image)
                        .into(imageView)
                }
            }
            pageCount = listArticle.size
        }
    }
}