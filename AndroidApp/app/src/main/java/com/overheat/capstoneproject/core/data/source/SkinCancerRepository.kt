package com.overheat.capstoneproject.core.data.source

import android.util.Log
import com.overheat.capstoneproject.core.data.NetworkBoundResource
import com.overheat.capstoneproject.core.data.Resource
import com.overheat.capstoneproject.core.data.source.local.LocalDataSource
import com.overheat.capstoneproject.core.data.source.remote.RemoteDataSource
import com.overheat.capstoneproject.core.data.source.remote.network.ApiResponse
import com.overheat.capstoneproject.core.data.source.remote.response.ArticleResponse
import com.overheat.capstoneproject.core.data.source.remote.response.FaqResponse
import com.overheat.capstoneproject.core.domain.model.Article
import com.overheat.capstoneproject.core.domain.model.DetailArticle
import com.overheat.capstoneproject.core.domain.model.Faq
import com.overheat.capstoneproject.core.domain.repository.ISkinCancerRepository
import com.overheat.capstoneproject.core.utils.AppExecutors
import com.overheat.capstoneproject.core.utils.DataMapper
import com.overheat.capstoneproject.core.utils.SkinCancerPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.lang.Exception

class SkinCancerRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val appExecutors: AppExecutors,
    private val sharedPreferences: SkinCancerPreferences
) : ISkinCancerRepository {

    override fun getAllFaqs(): Flow<Resource<List<Faq>>> {
        return object : NetworkBoundResource<List<Faq>, List<FaqResponse>>(appExecutors) {
            override fun loadFromDB(): Flow<List<Faq>> {
                return localDataSource.getAllFaqs().map {
                    DataMapper.mapEntityToDomainFaq(it)
                }
            }

            override fun shouldFetch(data: List<Faq>?): Boolean {
                return data == null || data.isEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponse<List<FaqResponse>>> {
                return remoteDataSource.getAllFaqs()
            }

            override suspend fun saveCallResult(data: List<FaqResponse>) {
                val listFaqs = DataMapper.mapResponseToEntityFaq(data)
                localDataSource.insertAllFaqs(listFaqs)
            }
        }.asFlow()
    }

    override fun getAllArticles(): Flow<Resource<List<Article>>> {
        return object : NetworkBoundResource<List<Article>, List<ArticleResponse>>(appExecutors) {
            override fun loadFromDB(): Flow<List<Article>> {
                return localDataSource.getAllArticles().map {
                    DataMapper.mapEntityToDomainArticle(it)
                }
            }

            override fun shouldFetch(data: List<Article>?): Boolean {
                return data == null || data.isEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponse<List<ArticleResponse>>> {
                return remoteDataSource.getAllArticles()
            }

            override suspend fun saveCallResult(data: List<ArticleResponse>) {
                val listArticles = DataMapper.mapResponseToEntityArticle(data)
                localDataSource.insertAllArticles(listArticles)
            }
        }.asFlow()
    }

    override fun getDetailArticle(articleId: Int): DetailArticle? {
        var detailArticle: DetailArticle? = null

        try {
            remoteDataSource.getDetailArticleVoid(articleId) {
                if (it != null) {
                    detailArticle = DataMapper.mapResponseToDomainDetailArticle(it)
                }
            }
        } catch (e: Exception) {
            Log.e("Repository", e.toString())
        }

        Log.d("Repository", "DetailArticle: ${detailArticle.toString()}")
        return detailArticle
    }

    override suspend fun sendComment(articleId: Int, comment: String) : Boolean {
        try {
            return when (val response = remoteDataSource.sendComment(articleId, comment)) {
                is ApiResponse.Success -> {
                    true
                }
                is ApiResponse.Error -> {
                    Log.e("sendComment", response.errorMessage)
                    false
                }
                else -> {
                    false
                }
            }
        } catch (e: Exception) {
            Log.e("Repository", e.toString())
            return false
        }
    }

    override fun getActiveToken(email: String, passHash: String) {
        remoteDataSource.getActiveTokenVoid(email, passHash) {
            if (it != null) {
                sharedPreferences.setToken(it.token, it.isValid)
            }
        }
    }

    override fun addNewUser(name: String, email: String, passHash: String) {
        remoteDataSource.addNewUserVoid(name, email, passHash) {
            if (it != null) {
                sharedPreferences.setName(it.name)
                sharedPreferences.setEmail(it.email)

                // Add to database
//                localDataSource.addNewUser()
            }
        }
    }

    override fun userLogout() {
        remoteDataSource.userLogoutVoid {
            if (it != null) {
                sharedPreferences.setToken(it.token, it.isValid)
            }
        }
    }

    override fun getUserName(): String? {
        return sharedPreferences.getName()
    }
}