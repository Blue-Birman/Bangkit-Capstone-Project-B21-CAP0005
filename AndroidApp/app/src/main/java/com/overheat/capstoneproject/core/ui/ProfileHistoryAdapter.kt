package com.overheat.capstoneproject.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.overheat.capstoneproject.core.domain.model.Diagnose
import com.overheat.capstoneproject.core.domain.model.History
import com.overheat.capstoneproject.databinding.ItemsHistoryBinding

class ProfileHistoryAdapter
    : RecyclerView.Adapter<ProfileHistoryAdapter.ProfileHistoryViewHolder>() {

    private val listHistory = ArrayList<Diagnose>()

    fun setHistory(listHistory: List<Diagnose>?) {
        if (listHistory == null) return

        this.listHistory.clear()
        this.listHistory.addAll(listHistory)
        notifyDataSetChanged()
    }
    inner class ProfileHistoryViewHolder(private val binding: ItemsHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(history: Diagnose) {
            with(binding) {
                tvActivity.text = history.dateAdded
                tvDetailActivity.text = history.cancerProba.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileHistoryViewHolder {
        val binding = ItemsHistoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ProfileHistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProfileHistoryViewHolder, position: Int) {
        holder.bind(listHistory[position])
    }

    override fun getItemCount(): Int {
        return listHistory.size
    }
}