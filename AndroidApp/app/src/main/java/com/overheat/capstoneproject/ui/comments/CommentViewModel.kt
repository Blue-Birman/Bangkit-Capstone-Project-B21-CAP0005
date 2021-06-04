package com.overheat.capstoneproject.ui.comments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.overheat.capstoneproject.core.data.source.remote.RemoteDataSource
import com.overheat.capstoneproject.core.domain.model.Comment
import com.overheat.capstoneproject.core.domain.usecase.SkinCancerUseCase
import com.overheat.capstoneproject.core.utils.DataMapper

class CommentViewModel(
    private val useCase: SkinCancerUseCase,
    private val remote: RemoteDataSource
) : ViewModel() {

    val comments: MutableLiveData<List<Comment>?> = MutableLiveData()

    suspend fun postComment(articleId: Int, comment: String) {
        useCase.sendComment(articleId, comment)
        getListComment(articleId)
    }

    fun getListComment(articleId: Int) {
        comments.value = null
        remote.getDetailArticleVoid(articleId) {
            if (it != null) {
                comments.value = DataMapper.mapResponseToDomainComments(it.comments)
            }
        }
    }

    fun isLogin() : Boolean {
        return useCase.getUserName() != null
    }
}