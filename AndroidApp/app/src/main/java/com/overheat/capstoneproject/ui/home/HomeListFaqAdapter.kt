package com.overheat.capstoneproject.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.overheat.capstoneproject.R
import com.overheat.capstoneproject.databinding.ItemsFaqBinding

class HomeListFaqAdapter : RecyclerView.Adapter<HomeListFaqAdapter.HomeListFaqViewHolder>() {

    inner class HomeListFaqViewHolder(private val binding: ItemsFaqBinding) : RecyclerView.ViewHolder(binding.root) {
        var isSelected: Boolean = false

        fun bind() {
            with(binding) {
                tvFaqQuestion.text = "Hello nama kamu dari kelompok mana?"
                tvFaqAnswer.text = "Kami dari kelompok Overheat"

                itemView.setOnClickListener {
                    if (isSelected) {
                        tvFaqAnswer.visibility = View.GONE
                        tvFaqQuestion.setCompoundDrawablesWithIntrinsicBounds(
                            0, 0, R.drawable.ic_arrow_down_black, 0
                        )
                    } else {
                        tvFaqAnswer.visibility = View.VISIBLE
                        tvFaqQuestion.setCompoundDrawablesWithIntrinsicBounds(
                            0, 0, R.drawable.ic_arrow_down_black, 0
                        )
                    }

                    isSelected = !isSelected
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeListFaqViewHolder {
        val binding = ItemsFaqBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return HomeListFaqViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeListFaqViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return 3
    }
}