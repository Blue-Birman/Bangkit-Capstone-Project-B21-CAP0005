package com.overheat.capstoneproject.ui.news

import android.os.Bundle
import android.util.Base64
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.overheat.capstoneproject.R
import com.overheat.capstoneproject.core.domain.model.DetailArticle
import com.overheat.capstoneproject.databinding.ActivityDetailNewsBinding
import org.koin.android.viewmodel.ext.android.viewModel

class DetailNewsActivity : AppCompatActivity() {

    companion object {
        const val ARTICLE_ID = "article_id"
    }

    private val viewModel: DetailNewsViewModel by viewModel()
    private lateinit var binding: ActivityDetailNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.newsToolbar)

        val articleId = intent.getIntExtra(ARTICLE_ID, 0)
        viewModel.detailArticle(articleId)
        binding.progressBarDetailNews.visibility = View.VISIBLE
        viewModel.detailArticle.observe(this, { data ->
            if (data != null) {
                binding.progressBarDetailNews.visibility = View.GONE
                populateActivity(data)
            }
        })
    }

    private fun populateActivity(detail: DetailArticle) {
        with(binding) {
            if (detail.article.image != null) {
                val decodedImage = Base64.decode(detail.article.image, Base64.DEFAULT)
                Glide.with(this@DetailNewsActivity)
                    .load(decodedImage)
                    .into(ivNews)
            } else {
                Glide.with(this@DetailNewsActivity)
                    .load(R.drawable.no_image)
                    .into(ivNews)
            }

            tvContent.text = detail.article.article
            collapsingToolbarLayout.title = detail.article.title
        }
    }
}