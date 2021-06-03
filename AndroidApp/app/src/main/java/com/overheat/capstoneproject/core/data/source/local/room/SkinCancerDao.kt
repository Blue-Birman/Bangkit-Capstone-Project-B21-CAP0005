package com.overheat.capstoneproject.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.overheat.capstoneproject.core.data.source.local.entity.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SkinCancerDao {

    @Query("SELECT * FROM faq")
    fun getAllFaqs() : Flow<List<FaqEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllFaqs(listFaqs: List<FaqEntity>)

    @Query("SELECT * FROM article")
    fun getAllArticle() : Flow<List<ArticleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllArticle(listArticles: List<ArticleEntity>)

    @Query("SELECT * FROM diagnose WHERE user_id = :userId")
    fun getAllUserDiagnose(userId: Int) : Flow<List<DiagnoseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllDiagnose(listDiagnose: List<DiagnoseEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewUser(newUser: UserEntity)

    @Query("SELECT * FROM comment WHERE article_id = :articleId")
    fun getAllCommentsArticle(articleId: Int) : List<CommentEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllComments(listComment: List<CommentEntity>)

    @Query("SELECT * FROM article WHERE id = :articleId")
    fun getSpecificArticle(articleId: Int) : ArticleEntity
}