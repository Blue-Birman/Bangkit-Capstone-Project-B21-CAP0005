package com.overheat.capstoneproject.ui.news

import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.overheat.capstoneproject.R
import com.overheat.capstoneproject.core.domain.model.DetailArticle
import com.overheat.capstoneproject.databinding.ActivityDetailNewsBinding
import com.overheat.capstoneproject.ui.comments.CommentActivity
import org.koin.android.viewmodel.ext.android.viewModel

class DetailNewsActivity : AppCompatActivity() {

    companion object {
        const val ARTICLE_ID = "article_id"
    }

    private val viewModel: DetailNewsViewModel by viewModel()
    private lateinit var binding: ActivityDetailNewsBinding
    private var isLiked = false
    private var likes = 0
    private var comments = 0

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

            // Update likes and comments count
            tvLike.text = resources.getString(R.string.text_likes, likes.toString())
            tvComment.text = resources.getString(R.string.text_comments, comments.toString())

            // On click listener for image view
            imgLike.setOnClickListener {
                isLiked = !isLiked
                if (isLiked) {
                    likes += 1
                    tvLike.text = resources.getString(R.string.text_likes, likes.toString())
                    tvLike.setTextColor(resources.getColor(R.color.blue))
                } else {
                    likes -= 1
                    tvLike.text = resources.getString(R.string.text_likes, likes.toString())
                    tvLike.setTextColor(resources.getColor(R.color.black))
                }
            }

            imgComment.setOnClickListener {
                val intent = Intent(this@DetailNewsActivity, CommentActivity::class.java)
                intent.putExtra(CommentActivity.ARTICLE_ID_EXTRA, detail.article.id)
                startActivity(intent)
            }
            bottomNavigation.visibility = View.VISIBLE
        }
    }
}