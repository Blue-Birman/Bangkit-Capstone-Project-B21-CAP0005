package com.overheat.capstoneproject.ui.comments

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.overheat.capstoneproject.MainActivity
import com.overheat.capstoneproject.R
import com.overheat.capstoneproject.core.ui.CommentAdapter
import com.overheat.capstoneproject.databinding.ActivityCommentBinding
import kotlinx.coroutines.runBlocking
import org.koin.android.viewmodel.ext.android.viewModel

class CommentActivity : AppCompatActivity() {

    companion object {
        const val ARTICLE_ID_EXTRA = "extra_article_id"
    }

    private val viewModel: CommentViewModel by viewModel()
    private lateinit var binding: ActivityCommentBinding
    private var articleId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = resources.getString(R.string.subtitle_comments)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        articleId = intent.getIntExtra(ARTICLE_ID_EXTRA, 0)
        fillCommentActivity()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> { onBackPressed() }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun fillCommentActivity() {
        val adapter = CommentAdapter()
        viewModel.getListComment(articleId)
        binding.progressBarComment.visibility = View.VISIBLE
        viewModel.comments.observe(this, { data ->
            if (data != null) {
                binding.progressBarComment.visibility = View.GONE
                adapter.setComment(data)
            }
        })

        binding.rvComments.layoutManager = LinearLayoutManager(this)
        binding.rvComments.setHasFixedSize(true)
        binding.rvComments.adapter = adapter

        if (viewModel.isLogin()) {
            binding.containerComment.visibility = View.VISIBLE
            binding.containerGuest.visibility = View.GONE

            binding.btnSendComment.setOnClickListener {
                val comment = binding.edtComment.text.toString()
                binding.progressBarComment.visibility = View.VISIBLE
                runBlocking {
                    viewModel.postComment(articleId, comment)
                }
            }
        } else {
            binding.containerComment.visibility = View.GONE
            binding.containerGuest.visibility = View.VISIBLE

            binding.btnLogIn.setOnClickListener {
                val intent = Intent(this@CommentActivity, MainActivity::class.java)
                intent.putExtra(MainActivity.EXTRA_FRAGMENT, 4)
                intent.putExtra(MainActivity.EXTRA_TYPE, 2)
                startActivity(intent)
            }
            binding.btnCreateAccount.setOnClickListener {
                val intent = Intent(this@CommentActivity, MainActivity::class.java)
                intent.putExtra(MainActivity.EXTRA_FRAGMENT, 4)
                intent.putExtra(MainActivity.EXTRA_TYPE, 3)
                startActivity(intent)
            }
        }
    }
}