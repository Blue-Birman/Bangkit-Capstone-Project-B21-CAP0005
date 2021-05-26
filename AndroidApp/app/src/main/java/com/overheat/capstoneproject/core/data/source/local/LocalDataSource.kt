package com.overheat.capstoneproject.core.data.source.local

import com.overheat.capstoneproject.core.data.source.local.entity.ArticleEntity
import com.overheat.capstoneproject.core.data.source.local.entity.FaqEntity
import com.overheat.capstoneproject.core.data.source.local.room.SkinCancerDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(
    private val databaseDao: SkinCancerDao
) {

    fun getAllFaqs() : Flow<List<FaqEntity>> =
        databaseDao.getAllFaqs()

    suspend fun insertAllFaqs(faqs: List<FaqEntity>) =
        databaseDao.insertAllFaqs(faqs)

    fun getAllArticles() : Flow<List<ArticleEntity>> =
        databaseDao.getAllArticle()

    suspend fun insertAllArticles(articles: List<ArticleEntity>) =
        databaseDao.insertAllArticle(articles)
}