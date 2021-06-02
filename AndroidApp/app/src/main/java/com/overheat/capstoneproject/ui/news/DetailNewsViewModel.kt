package com.overheat.capstoneproject.ui.news

import androidx.lifecycle.ViewModel
import com.overheat.capstoneproject.core.domain.model.DetailArticle
import com.overheat.capstoneproject.core.domain.usecase.SkinCancerUseCase

class DetailNewsViewModel(private val useCase: SkinCancerUseCase) : ViewModel() {

    fun detailArticle(articleId: Int) : DetailArticle =
        useCase.getDetailArticle(articleId) as DetailArticle
}