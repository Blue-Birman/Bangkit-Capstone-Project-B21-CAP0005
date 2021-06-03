package com.overheat.capstoneproject.core.domain.usecase

import com.overheat.capstoneproject.core.data.Resource
import com.overheat.capstoneproject.core.domain.model.Article
import com.overheat.capstoneproject.core.domain.model.DetailArticle
import com.overheat.capstoneproject.core.domain.model.Diagnose
import com.overheat.capstoneproject.core.domain.model.Faq
import com.overheat.capstoneproject.core.domain.model.Result
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

    override fun getDetailArticle(articleId: Int): DetailArticle? {
        return repository.getDetailArticle(articleId)
    }

    override suspend fun sendComment(articleId: Int, comment: String): Boolean {
        return repository.sendComment(articleId, comment)
    }

    override fun getAllHistoryDiagnose(userId: Int): Flow<Resource<List<Diagnose>>> {
        return repository.getAllHistoryDiagnose(userId)
    }

    override fun getResultFromImage(image: String): Result? {
        return repository.getResultFromImage(image)
    }

    override suspend fun getDiagnoseResult(resultId: Int): Diagnose? {
        return getDiagnoseResult(resultId)
    }

    override fun getActiveToken(email: String, passHash: String) {
        return getActiveToken(email, passHash)
    }

    override fun addNewUser(name: String, email: String, passHash: String) {
        return addNewUser(name, email, passHash)
    }

    override fun userLogout() {
        return userLogout()
    }
}