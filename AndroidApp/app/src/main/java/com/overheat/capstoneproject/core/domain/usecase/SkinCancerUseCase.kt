package com.overheat.capstoneproject.core.domain.usecase

import com.overheat.capstoneproject.core.data.Resource
import com.overheat.capstoneproject.core.domain.model.Article
import com.overheat.capstoneproject.core.domain.model.DetailArticle
import com.overheat.capstoneproject.core.domain.model.Diagnose
import com.overheat.capstoneproject.core.domain.model.Faq
import com.overheat.capstoneproject.core.domain.model.Result
import kotlinx.coroutines.flow.Flow

interface SkinCancerUseCase {

    fun getAllFaqs() : Flow<Resource<List<Faq>>>
    fun getAllArticles() : Flow<Resource<List<Article>>>
    fun getDetailArticle(articleId: Int) : DetailArticle?
    suspend fun sendComment(articleId: Int, comment: String) : Boolean
    fun getAllHistoryDiagnose(userId: Int) : Flow<Resource<List<Diagnose>>>
    suspend fun getResultFromImage(image: String) : Result?
    suspend fun getDiagnoseResult(resultId: Int) : Diagnose?
    fun getActiveToken(email: String, passHash: String)
    fun addNewUser(name: String, email: String, passHash: String)
    fun userLogout()
}