package com.overheat.capstoneproject.core.data.source.local

import com.overheat.capstoneproject.core.data.source.local.entity.*
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

    fun getAllUserDiagnose(userId: Int) =
        databaseDao.getAllUserDiagnose(userId)

    suspend fun insertAllDiagnose(listDiagnose: List<DiagnoseEntity>) =
        databaseDao.insertAllDiagnose(listDiagnose)

    fun addNewUser(newUser: UserEntity) =
        databaseDao.insertNewUser(newUser)

    fun getAllComments(articleId: Int) : List<CommentEntity> =
        databaseDao.getAllCommentsArticle(articleId)

    suspend fun insertAllComments(listComment: List<CommentEntity>) =
        databaseDao.insertAllComments(listComment)

    fun getDetailArticle(articleId: Int) : ArticleEntity =
        databaseDao.getSpecificArticle(articleId)
}