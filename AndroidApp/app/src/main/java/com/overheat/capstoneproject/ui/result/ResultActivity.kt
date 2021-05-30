package com.overheat.capstoneproject.ui.result

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.overheat.capstoneproject.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val image = intent.getParcelableExtra<Bitmap>(EXTRA_IMAGE)
        val imageString = intent.getStringExtra(EXTRA_STRING)

        binding.ivResult.setImageBitmap(image)
        binding.tvImageString.text = imageString
    }

    companion object {
        const val EXTRA_IMAGE = "extra_image"
        const val EXTRA_STRING = "extra_string"
    }
}