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
}