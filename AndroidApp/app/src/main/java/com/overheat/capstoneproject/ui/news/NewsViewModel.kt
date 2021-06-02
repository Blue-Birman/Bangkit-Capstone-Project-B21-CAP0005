package com.overheat.capstoneproject.ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.overheat.capstoneproject.core.data.Resource
import com.overheat.capstoneproject.core.domain.model.Article
import com.overheat.capstoneproject.core.domain.usecase.SkinCancerUseCase

class NewsViewModel(private val useCase: SkinCancerUseCase) : ViewModel() {

    fun articles(): LiveData<Resource<List<Article>>> {
        return useCase.getAllArticles().asLiveData()
    }
}