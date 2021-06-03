package com.overheat.capstoneproject.core.data.source.remote

import android.util.Log
import com.overheat.capstoneproject.core.data.source.remote.network.ApiResponse
import com.overheat.capstoneproject.core.data.source.remote.network.ApiService
import com.overheat.capstoneproject.core.data.source.remote.response.*
import com.overheat.capstoneproject.core.utils.JsonHelper
import com.overheat.capstoneproject.core.utils.SkinCancerPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.internal.wait
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class RemoteDataSource(
    private val jsonHelper: JsonHelper,
    private val apiService: ApiService,
    private val sharedPreferences: SkinCancerPreferences
) {

    fun getAllFaqs() : Flow<ApiResponse<List<FaqResponse>>> {
        return flow {
            try {
                val response = jsonHelper.readFaqDataset()
                val listFaqs = response.data

                if (listFaqs.isNotEmpty()) {
                    emit(ApiResponse.Success(response.data))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getAllArticles() : Flow<ApiResponse<List<ArticleResponse>>> {
        return flow {
            try {
                val response = apiService.getListArticles()

                if (response.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getDetailArticleVoid(articleId: Int, onResult: (DetailArticleResponse?) -> Unit) {
        val bodyJson = JSONObject()
        bodyJson.put("article_id", articleId)

        val requestBody = bodyJson.toString()
            .toRequestBody("application/json".toMediaTypeOrNull())

        apiService.getDetailArticles(requestBody)
            .enqueue(object : Callback<DetailArticleResponse> {
                override fun onResponse(
                    call: Call<DetailArticleResponse>,
                    response: Response<DetailArticleResponse>
                ) {
                    Log.d("getDetailArticleVoid", response.body().toString())
                    onResult(response.body())
                }

                override fun onFailure(call: Call<DetailArticleResponse>, t: Throwable) {
                    onResult(null)
                }
            })
    }

    fun getDetailArticle(articleId: Int) : ApiResponse<DetailArticleResponse> {
        val bodyJson = JSONObject()
        bodyJson.put("article_id", articleId)

        val requestBody = bodyJson.toString()
            .toRequestBody("application/json".toMediaTypeOrNull())

        var detailArticle: DetailArticleResponse? = null
        val response = apiService.getDetailArticles(requestBody)
            .enqueue(object : Callback<DetailArticleResponse> {
            override fun onResponse(
                call: Call<DetailArticleResponse>,
                response: Response<DetailArticleResponse>
            ) {
                if (response.isSuccessful) {
                    Log.d("getDetailArticleRepo", response.body().toString())
                    detailArticle = response.body()
                }
            }

            override fun onFailure(call: Call<DetailArticleResponse>, t: Throwable) {
                Log.d("getDetailArticleRepo", "Response unsuccessful")
            }
        })
        return try {
            Log.d("getDetailArticleRepo", response.wait().toString())
            ApiResponse.Success(detailArticle!!)
        } catch (e: Exception) {
            Log.e("getDetailArticleRepo", e.toString())
            ApiResponse.Error(e.toString())
        }
    }

    suspend fun sendComment(articleId: Int, comment: String) : ApiResponse<String> {
        val bodyJson = JSONObject()
        bodyJson.put("article_id", articleId)
        bodyJson.put("comment", comment)
        bodyJson.put("email", sharedPreferences.getEmail())
        bodyJson.put("token", sharedPreferences.getToken())

        val requestBody = bodyJson.toString()
            .toRequestBody("application/json".toMediaTypeOrNull())

        return try {
            apiService.postComment(requestBody)
            ApiResponse.Success("Comment posted successfully")
        } catch (e: Exception) {
            Log.e("Post comment error", e.toString())
            ApiResponse.Error(e.toString())
        }
    }

    fun getAllHistoryDiagnose() : Flow<ApiResponse<List<DiagnoseResponse>>> {
        val bodyJson = JSONObject()
        bodyJson.put("email", sharedPreferences.getEmail())
        bodyJson.put("token", sharedPreferences.getToken())

        val requestBody = bodyJson.toString()
            .toRequestBody("application/json".toMediaTypeOrNull())

        return flow {
            try {
                val response = apiService.getHistoryDiagnose(requestBody)

                if (response.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getResultFromImage(image: String) : ApiResponse<ResultResponse> {
        val bodyJson = JSONObject()
        bodyJson.put("email", sharedPreferences.getEmail())
        bodyJson.put("token", sharedPreferences.getToken())
        bodyJson.put("image", image)

        val requestBody = bodyJson.toString()
            .toRequestBody("application/json".toMediaTypeOrNull())

        return try {
            val response = apiService.postImage(requestBody)
            ApiResponse.Success(response)
        } catch (e: Exception) {
            ApiResponse.Error(e.toString())
        }
    }

    fun getDiagnoseResult(resultId: Int) : ApiResponse<DiagnoseResponse> {
        val bodyJson = JSONObject()
        bodyJson.put("result_id", resultId)
        bodyJson.put("email", sharedPreferences.getEmail())
        bodyJson.put("token", sharedPreferences.getToken())

        val requestBody = bodyJson.toString()
            .toRequestBody("application/json".toMediaTypeOrNull())

        return try {
            val response = apiService.getDiagnoseResult(requestBody)
            ApiResponse.Success(response)
        } catch (e: Exception) {
            ApiResponse.Error(e.toString())
        }
    }

    fun getActiveToken(email: String, passHash: String) : ApiResponse<TokenResponse> {
        val bodyJson = JSONObject()
        bodyJson.put("email", email)
        bodyJson.put("pass_hash", passHash)

        val requestBody = bodyJson.toString()
            .toRequestBody("application/json".toMediaTypeOrNull())

        return try {
            val response = apiService.postLogin(requestBody)
            ApiResponse.Success(response)
        } catch (e: Exception) {
            ApiResponse.Error(e.toString())
        }
    }

    fun addNewUser(name: String, email: String, passHash: String) : ApiResponse<UserResponse> {
        val bodyJson = JSONObject()
        bodyJson.put("name", name)
        bodyJson.put("email", email)
        bodyJson.put("pass_hash", passHash)

        val requestBody = bodyJson.toString()
            .toRequestBody("application/json".toMediaTypeOrNull())

        return try {
            val response = apiService.postNewUser(requestBody)
            ApiResponse.Success(response)
        } catch (e: Exception) {
            ApiResponse.Error(e.toString())
        }
    }

    fun userLogout() : ApiResponse<TokenResponse> {
        val bodyJson = JSONObject()
        bodyJson.put("email", "email_here")
        bodyJson.put("token", "token_here")

        val requestBody = bodyJson.toString()
            .toRequestBody("application/json".toMediaTypeOrNull())

        return try {
            val response = apiService.postLogout(requestBody)
            ApiResponse.Success(response)
        } catch (e: Exception) {
            ApiResponse.Error(e.toString())
        }
    }
}