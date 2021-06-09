package com.overheat.capstoneproject.core.domain.usecase

import com.overheat.capstoneproject.core.data.Resource
import com.overheat.capstoneproject.core.domain.model.Article
import com.overheat.capstoneproject.core.domain.model.Faq
import kotlinx.coroutines.flow.Flow

interface SkinCancerUseCase {

    fun getAllFaqs() : Flow<Resource<List<Faq>>>
    fun getAllArticles() : Flow<Resource<List<Article>>>
    suspend fun sendComment(articleId: Int, comment: String) : Boolean
    fun getActiveToken(email: String, passHash: String)
    fun addNewUser(name: String, email: String, passHash: String)
    fun userLogout()
    fun getUserName() : String?
    fun getUserEmail() : String?
    fun getUserPasswordHash() : String?
    fun setUserLogin(name: String, email: String, passHash: String, token: String, isValid: Boolean)
}