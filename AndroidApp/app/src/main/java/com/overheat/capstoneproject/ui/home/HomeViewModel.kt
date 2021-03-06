package com.overheat.capstoneproject.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.overheat.capstoneproject.core.data.Resource
import com.overheat.capstoneproject.core.domain.model.Article
import com.overheat.capstoneproject.core.domain.model.Faq
import com.overheat.capstoneproject.core.domain.usecase.SkinCancerUseCase

class HomeViewModel(private val useCase: SkinCancerUseCase) : ViewModel() {

    fun faqs(): LiveData<Resource<List<Faq>>> {
        return useCase.getAllFaqs().asLiveData()
    }

    fun articles(): LiveData<Resource<List<Article>>> {
        return useCase.getAllArticles().asLiveData()
    }

    fun username(): String {
        return useCase.getUserName() ?: "Guest"
    }
}