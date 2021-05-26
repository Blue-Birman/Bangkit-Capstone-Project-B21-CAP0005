package com.overheat.capstoneproject.core.domain.repository

import com.overheat.capstoneproject.core.data.Resource
import com.overheat.capstoneproject.core.domain.model.Article
import com.overheat.capstoneproject.core.domain.model.Faq
import kotlinx.coroutines.flow.Flow

interface ISkinCancerRepository {

    fun getAllFaqs() : Flow<Resource<List<Faq>>>
    fun getAllArticles() : Flow<Resource<List<Article>>>
}