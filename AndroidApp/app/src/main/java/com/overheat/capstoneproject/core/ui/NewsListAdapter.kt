package com.overheat.capstoneproject.core.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.overheat.capstoneproject.databinding.ItemsNewsBinding
import com.overheat.capstoneproject.ui.news.DetailNewsActivity

class NewsListAdapter : RecyclerView.Adapter<NewsListAdapter.NewsListViewHolder>() {

    inner class NewsListViewHolder(private val binding: ItemsNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        val imgPath = "https://raw.githubusercontent.com/sayyam/carouselview/master/sample/src/main/res/drawable/image_3.jpg"

        fun bind() {
            with(binding) {
                tvArticleTitle.text = "Lorem ipsum dolor sit amet"
                tvArticleText.text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Id quisque vestibulum ante quis. Nisl at arcu non dui aliquet felis phasellus aliquam "

                Glide.with(itemView.context)
                    .load(imgPath)
                    .into(imgNewsCover)

                itemView.setOnClickListener {
                    // Go to the article using Intent
                    val intent = Intent(itemView.context, DetailNewsActivity::class.java)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsListViewHolder {
        val binding = ItemsNewsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NewsListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsListViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return 5
    }
}