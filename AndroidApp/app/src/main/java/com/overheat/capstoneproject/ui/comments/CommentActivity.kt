package com.overheat.capstoneproject.ui.comments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.overheat.capstoneproject.R
import com.overheat.capstoneproject.databinding.ActivityCommentBinding

class CommentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCommentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = resources.getString(R.string.subtitle_comments)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}