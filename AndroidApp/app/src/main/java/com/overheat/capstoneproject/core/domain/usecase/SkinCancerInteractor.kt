package com.overheat.capstoneproject.core.domain.usecase

import com.overheat.capstoneproject.core.data.Resource
import com.overheat.capstoneproject.core.domain.model.Article
import com.overheat.capstoneproject.core.domain.model.Faq
import com.overheat.capstoneproject.core.domain.repository.ISkinCancerRepository
import kotlinx.coroutines.flow.Flow

class SkinCancerInteractor(
    private val repository: ISkinCancerRepository
) : SkinCancerUseCase {

    override fun getAllFaqs(): Flow<Resource<List<Faq>>> {
        return repository.getAllFaqs()
    }

    override fun getAllArticles(): Flow<Resource<List<Article>>> {
        return repository.getAllArticles()
    }

    override suspend fun sendComment(articleId: Int, comment: String): Boolean {
        return repository.sendComment(articleId, comment)
    }

    override fun getActiveToken(email: String, passHash: String) {
        return repository.getActiveToken(email, passHash)
    }

    override fun addNewUser(name: String, email: String, passHash: String) {
        return repository.addNewUser(name, email, passHash)
    }

    override fun userLogout() {
        return repository.userLogout()
    }

    override fun getUserName() : String? {
        return repository.getUserName()
    }

    override fun getUserEmail(): String? {
        return repository.getUserEmail()
    }

    override fun getUserPasswordHash(): String? {
        return repository.getUserPasswordHash()
    }

    override fun setUserLogin(name: String, email: String, passHash: String, token: String, isValid: Boolean) {
        repository.setUser(name, email, passHash, token, isValid)
    }
}