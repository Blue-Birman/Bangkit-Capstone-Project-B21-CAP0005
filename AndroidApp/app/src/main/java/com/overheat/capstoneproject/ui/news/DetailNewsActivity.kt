package com.overheat.capstoneproject.ui.news

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.overheat.capstoneproject.R
import com.overheat.capstoneproject.databinding.ActivityDetailNewsBinding

class DetailNewsActivity : AppCompatActivity() {

    companion object {
        const val ARTICLE_ID = "article_id"
    }

    private lateinit var binding: ActivityDetailNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.newsToolbar)
        supportActionBar?.title = resources.getString(R.string.title_news)
    }
}