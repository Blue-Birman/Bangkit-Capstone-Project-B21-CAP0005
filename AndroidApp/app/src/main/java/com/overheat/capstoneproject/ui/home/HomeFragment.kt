package com.overheat.capstoneproject.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.overheat.capstoneproject.R
import com.overheat.capstoneproject.core.data.Resource
import com.overheat.capstoneproject.core.domain.model.Article
import com.overheat.capstoneproject.core.ui.HomeListFaqAdapter
import com.overheat.capstoneproject.databinding.FragmentHomeBinding
import com.overheat.capstoneproject.ui.news.DetailNewsActivity
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
            val username = viewModel.username()
            tvWelcomeUser.text = context?.resources?.getString(R.string.welcome_guest, username)
            tvWelcomeText.text = "lorem ipsum dolor sit amet"
        }
    }

    private fun prepareRecyclerView() {
        val adapter = HomeListFaqAdapter()
        viewModel.faqs().observe(viewLifecycleOwner, { listFaqs ->
            if (listFaqs != null) {
                when(listFaqs) {
                    is Resource.Success -> {
                        binding.progressBarRvFaqs.visibility = View.GONE
                        adapter.setDataFaqs(listFaqs.data)
                    }
                    is Resource.Loading -> {
                        binding.progressBarRvFaqs.visibility = View.VISIBLE
                    }
                    is Resource.Error -> {
                        binding.progressBarRvFaqs.visibility = View.GONE
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
                        binding.progressBarCarouselView.visibility = View.GONE
                        articles.data?.let { listArticle.addAll(it) }
                        fillImageCarousel(listArticle)
                    }
                    is Resource.Loading -> {
                        binding.progressBarCarouselView.visibility = View.VISIBLE
                    }
                    is Resource.Error -> {
                        // Error
                        binding.progressBarCarouselView.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun fillImageCarousel(listArticle: ArrayList<Article>) {
        var size = listArticle.size
        if (size > 5) {
            size = 5
        }

        with(binding.carouselView) {
            setImageListener { position, imageView ->
                val newestNews = listArticle.size - position - 1
                if (listArticle[position].image != null) {
                    val decodedImage = Base64.decode(listArticle[newestNews].image, Base64.DEFAULT)
                    Glide.with(this@HomeFragment)
                        .load(decodedImage)
                        .fitCenter()
                        .into(imageView)
                } else {
                    Glide.with(this@HomeFragment)
                        .load(R.drawable.no_image)
                        .fitCenter()
                        .into(imageView)
                }

                imageView.setOnClickListener {
                    val intent = Intent(context, DetailNewsActivity::class.java)
                    intent.putExtra(DetailNewsActivity.ARTICLE_ID, listArticle[newestNews].id)
                    startActivity(intent)
                }
            }
            pageCount = size
        }
    }
}