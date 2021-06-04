package com.overheat.capstoneproject.ui.comments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.overheat.capstoneproject.R
import com.overheat.capstoneproject.databinding.ActivityCommentBinding

class CommentActivity : AppCompatActivity() {

    companion object {
        const val ARTICLE_ID_EXTRA = "extra_article_id"
    }

    private lateinit var binding: ActivityCommentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = resources.getString(R.string.subtitle_comments)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val articleId = intent.getIntExtra(ARTICLE_ID_EXTRA, 0)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> { onBackPressed() }
        }
        return super.onOptionsItemSelected(item)
    }
}