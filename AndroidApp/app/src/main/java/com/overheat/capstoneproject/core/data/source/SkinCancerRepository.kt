package com.overheat.capstoneproject.core.data.source

import android.util.Log
import com.overheat.capstoneproject.core.data.NetworkBoundResource
import com.overheat.capstoneproject.core.data.Resource
import com.overheat.capstoneproject.core.data.source.local.LocalDataSource
import com.overheat.capstoneproject.core.data.source.remote.RemoteDataSource
import com.overheat.capstoneproject.core.data.source.remote.network.ApiResponse
import com.overheat.capstoneproject.core.data.source.remote.response.ArticleResponse
import com.overheat.capstoneproject.core.data.source.remote.response.DiagnoseResponse
import com.overheat.capstoneproject.core.data.source.remote.response.FaqResponse
import com.overheat.capstoneproject.core.domain.model.Article
import com.overheat.capstoneproject.core.domain.model.DetailArticle
import com.overheat.capstoneproject.core.domain.model.Diagnose
import com.overheat.capstoneproject.core.domain.model.Faq
import com.overheat.capstoneproject.core.domain.model.Result
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
            when (val response = remoteDataSource.getDetailArticle(articleId)) {
                is ApiResponse.Success -> {
                    detailArticle = DataMapper.mapResponseToDomainDetailArticle(response.data)
                }
                is ApiResponse.Error -> {
                    Log.e("getDetailArticle", response.errorMessage)
                }
                else -> {}
            }
        } catch (e: Exception) {
            Log.e("Repository", e.toString())
        }

        return detailArticle
    }

    override suspend fun sendComment(articleId: Int, comment: String) : Boolean {
        try {
            when (val response = remoteDataSource.sendComment(articleId, comment)) {
                is ApiResponse.Success -> {
                    return true
                }
                is ApiResponse.Error -> {
                    Log.e("sendComment", response.errorMessage)
                }
                else -> {}
            }
        } catch (e: Exception) {
            Log.e("Repository", e.toString())
        }

        return false
    }

    override fun getAllHistoryDiagnose(userId: Int): Flow<Resource<List<Diagnose>>> {
        return object : NetworkBoundResource<List<Diagnose>, List<DiagnoseResponse>>(appExecutors) {
            override fun loadFromDB(): Flow<List<Diagnose>> {
                return localDataSource.getAllUserDiagnose(userId).map {
                    DataMapper.mapEntityToDomainDiagnose(it)
                }
            }

            override fun shouldFetch(data: List<Diagnose>?): Boolean {
                return data == null || data.isEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponse<List<DiagnoseResponse>>> {
                return remoteDataSource.getAllHistoryDiagnose()
            }

            override suspend fun saveCallResult(data: List<DiagnoseResponse>) {
                val listDiagnose = DataMapper.mapResponseToEntityDiagnose(data)
                localDataSource.insertAllDiagnose(listDiagnose)
            }
        }.asFlow()
    }

    override suspend fun getResultFromImage(image: String): Result? {
        var result: Result? = null

        try {
            when (val response = remoteDataSource.getResultFromImage(image)) {
                is ApiResponse.Success -> {
                    result = DataMapper.mapResponseToDomainResult(response.data)
                }
                is ApiResponse.Error -> {
                    Log.e("getResultFromImage", response.errorMessage)
                }
                else -> {}
            }
        } catch (e: Exception) {
            Log.e("Repository", e.toString())
        }

        return result
    }

    override suspend fun getDiagnoseResult(resultId: Int): Diagnose? {
        var diagnose: Diagnose? = null

        try {
            when (val response = remoteDataSource.getDiagnoseResult(resultId)) {
                is ApiResponse.Success -> {
                    diagnose = DataMapper.mapResponseToDomainDiagnose(response.data)
                }
                is ApiResponse.Error -> {
                    Log.e("getDiagnoseResult", response.errorMessage)
                }
                else -> {}
            }
        } catch (e: Exception) {
            Log.e("Repository", e.toString())
        }

        return diagnose
    }

    override fun getActiveToken(email: String, passHash: String) {
        try {
            when (val response = remoteDataSource.getActiveToken(email, passHash)) {
                is ApiResponse.Success -> {
                    sharedPreferences.setToken(response.data.token, response.data.isValid)
                }
                is ApiResponse.Error -> {
                    Log.e("getActiveToken", response.errorMessage)
                }
                else -> {}
            }
        } catch (e: Exception) {
            Log.e("Repository", e.toString())
        }
    }

    override fun addNewUser(name: String, email: String, passHash: String) {
        try {
            when (val response = remoteDataSource.addNewUser(name, email, passHash)) {
                is ApiResponse.Success -> {
                    val userEntity = DataMapper.mapResponseToEntityUser(response.data)
                    localDataSource.addNewUser(userEntity)

                    sharedPreferences.setEmail(userEntity.email)
                }
                is ApiResponse.Error -> {
                    Log.e("addNewUser", response.errorMessage)
                }
                else -> {}
            }
        } catch (e: Exception) {
            Log.e("Repository", e.toString())
        }
    }

    override fun userLogout() {
        try {
            when (val response = remoteDataSource.userLogout()) {
                is ApiResponse.Success -> {
                    // Close the app
                }
                is ApiResponse.Error -> {
                    Log.e("userLogout", response.errorMessage)
                }
                else -> {}
            }
        } catch (e: Exception) {
            Log.e("Repository", e.toString())
        }
    }
}