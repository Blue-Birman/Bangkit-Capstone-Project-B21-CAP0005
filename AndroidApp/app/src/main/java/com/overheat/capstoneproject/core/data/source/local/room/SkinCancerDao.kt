package com.overheat.capstoneproject.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.overheat.capstoneproject.core.data.source.local.entity.ArticleEntity
import com.overheat.capstoneproject.core.data.source.local.entity.FaqEntity
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
}