package com.overheat.capstoneproject.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.overheat.capstoneproject.core.domain.model.Comment
import com.overheat.capstoneproject.databinding.ItemsHistoryBinding

class CommentAdapter
    : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    private val listComment = ArrayList<Comment>()

    fun setComment(listComment: List<Comment>?) {
        if (listComment == null) return

        this.listComment.clear()
        this.listComment.addAll(listComment)
        notifyDataSetChanged()
    }

    inner class CommentViewHolder(private val binding: ItemsHistoryBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(comment: Comment) {
            with(binding) {
                tvActivity.text = comment.dateAdded
                tvDetailActivity.text = comment.comment
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val binding = ItemsHistoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(listComment[position])
    }

    override fun getItemCount(): Int {
        return listComment.size
    }
}