package com.overheat.capstoneproject.core.ui

import android.content.Intent
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.overheat.capstoneproject.R
import com.overheat.capstoneproject.core.domain.model.Article
import com.overheat.capstoneproject.databinding.ItemsNewsBinding
import com.overheat.capstoneproject.ui.news.DetailNewsActivity

class NewsListAdapter : RecyclerView.Adapter<NewsListAdapter.NewsListViewHolder>() {

    private val listArticle = ArrayList<Article>()

    fun setArticles(listArticle: List<Article>?) {
        if (listArticle == null) return

        this.listArticle.clear()
        this.listArticle.addAll(listArticle)
        notifyDataSetChanged()
    }

    inner class NewsListViewHolder(private val binding: ItemsNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            with(binding) {
                tvArticleTitle.text = article.title
                tvArticleText.text = article.article

                if (article.image != null) {
                    val decodedImage = Base64.decode(article.image, Base64.DEFAULT)
                    Glide.with(itemView.context)
                        .load(decodedImage)
                        .into(imgNewsCover)
                } else {
                    Glide.with(itemView.context)
                        .load(R.drawable.no_image)
                        .into(imgNewsCover)
                }

                itemView.setOnClickListener {
                    // Go to the article using Intent
                    val intent = Intent(itemView.context, DetailNewsActivity::class.java)
                    intent.putExtra(DetailNewsActivity.ARTICLE_ID, article.id)
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
        holder.bind(listArticle[position])
    }

    override fun getItemCount(): Int {
        return listArticle.size
    }
}