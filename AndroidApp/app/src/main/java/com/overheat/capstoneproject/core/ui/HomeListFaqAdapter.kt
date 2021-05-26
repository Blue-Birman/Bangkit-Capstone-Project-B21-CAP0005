package com.overheat.capstoneproject.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.overheat.capstoneproject.R
import com.overheat.capstoneproject.core.domain.model.Faq
import com.overheat.capstoneproject.databinding.ItemsFaqBinding

class HomeListFaqAdapter : RecyclerView.Adapter<HomeListFaqAdapter.HomeListFaqViewHolder>() {

    private val listFaqs = ArrayList<Faq>()

    fun setDataFaqs(data: List<Faq>?) {
        if (data == null) return

        listFaqs.clear()
        listFaqs.addAll(data)
        notifyDataSetChanged()
    }

    inner class HomeListFaqViewHolder(private val binding: ItemsFaqBinding)
        : RecyclerView.ViewHolder(binding.root) {
        var isSelected: Boolean = false

        fun bind(faq: Faq) {
            with(binding) {
                tvFaqQuestion.text = faq.question
                tvFaqAnswer.text = faq.answer

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
        holder.bind(listFaqs[position])
    }

    override fun getItemCount(): Int {
        return listFaqs.size
    }
}