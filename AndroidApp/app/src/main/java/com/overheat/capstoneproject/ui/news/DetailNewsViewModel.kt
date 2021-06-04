package com.overheat.capstoneproject.ui.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.overheat.capstoneproject.core.data.source.remote.RemoteDataSource
import com.overheat.capstoneproject.core.domain.model.DetailArticle
import com.overheat.capstoneproject.core.domain.usecase.SkinCancerUseCase
import com.overheat.capstoneproject.core.utils.DataMapper

class DetailNewsViewModel(private val useCase: SkinCancerUseCase) : ViewModel() {

    var detailArticle: MutableLiveData<DetailArticle?> = MutableLiveData<DetailArticle?>()

//    fun detailArticle(articleId: Int) {
//        remote.getDetailArticleVoid(articleId) {
//            if (it != null) {
//                detailArticle.value = DataMapper.mapResponseToDomainDetailArticle(it)
//            }
//        }
//    }
    fun detailArticle(articleId: Int) {
        detailArticle.value = useCase.getDetailArticle(articleId)
    }
}