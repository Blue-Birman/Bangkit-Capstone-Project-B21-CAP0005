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
                    onResult(response.body())
                }

                override fun onFailure(call: Call<DetailArticleResponse>, t: Throwable) {
                    onResult(null)
                }
            })
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

    fun getAllHistoryDiagnoseVoid(onResult: (List<DiagnoseResponse>?) -> Unit) {
        val bodyJson = JSONObject()
        bodyJson.put("email", sharedPreferences.getEmail())
        bodyJson.put("token", sharedPreferences.getToken())

        val requestBody = bodyJson.toString()
            .toRequestBody("application/json".toMediaTypeOrNull())

        apiService.getAllUserDiagnoses(requestBody)
            .enqueue(object : Callback<List<DiagnoseResponse>> {
                override fun onResponse(
                    call: Call<List<DiagnoseResponse>>,
                    response: Response<List<DiagnoseResponse>>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<List<DiagnoseResponse>>, t: Throwable) {
                    onResult(null)
                }
            })
    }

    fun getResultFromImageVoid(image: String, onResult: (ResultResponse?) -> Unit) {
        val bodyJson = JSONObject()
        bodyJson.put("email", JSONObject.NULL)
        bodyJson.put("token", JSONObject.NULL)
        bodyJson.put("image", image)

        val requestBody = bodyJson.toString()
            .replace("\\", "")
            .toRequestBody("application/json".toMediaTypeOrNull())

        apiService.postImage(requestBody)
            .enqueue(object : Callback<ResultResponse> {
                override fun onResponse(
                    call: Call<ResultResponse>,
                    response: Response<ResultResponse>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<ResultResponse>, t: Throwable) {
                    onResult(null)
                }
            })
    }

    fun getDiagnoseResultVoid(resultId: Int, onResult: (DiagnoseResponse?) -> Unit) {
        val bodyJson = JSONObject()
        bodyJson.put("result_id", resultId)
        bodyJson.put("email", sharedPreferences.getEmail())
        bodyJson.put("token", sharedPreferences.getToken())

        val requestBody = bodyJson.toString()
            .toRequestBody("application/json".toMediaTypeOrNull())

        apiService.getDiagnoseResult(requestBody)
            .enqueue(object : Callback<DiagnoseResponse> {
                override fun onResponse(
                    call: Call<DiagnoseResponse>,
                    response: Response<DiagnoseResponse>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<DiagnoseResponse>, t: Throwable) {
                    onResult(null)
                }
            })
    }

    fun getActiveTokenVoid(email: String, passHash: String, onResult: (TokenResponse?) -> Unit) {
        val bodyJson = JSONObject()
        bodyJson.put("email", email)
        bodyJson.put("pass_hash", passHash)

        val requestBody = bodyJson.toString()
            .toRequestBody("application/json".toMediaTypeOrNull())

        apiService.postLogin(requestBody)
            .enqueue(object : Callback<TokenResponse> {
                override fun onResponse(
                    call: Call<TokenResponse>,
                    response: Response<TokenResponse>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                    onResult(null)
                }
            })
    }

    fun addNewUserVoid(
        name: String,
        email: String,
        passHash: String,
        onResult: (UserResponse?) -> Unit
    ) {
        val bodyJson = JSONObject()
        bodyJson.put("name", name)
        bodyJson.put("email", email)
        bodyJson.put("pass_hash", passHash)

        val requestBody = bodyJson.toString()
            .toRequestBody("application/json".toMediaTypeOrNull())

        apiService.postNewUser(requestBody)
            .enqueue(object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    onResult(null)
                }
            })
    }

    fun userLogoutVoid(onResult: (TokenResponse?) -> Unit) {
        val bodyJson = JSONObject()
        bodyJson.put("email", "email_here")
        bodyJson.put("token", "token_here")

        val requestBody = bodyJson.toString()
            .toRequestBody("application/json".toMediaTypeOrNull())

        apiService.postLogout(requestBody)
            .enqueue(object : Callback<TokenResponse> {
                override fun onResponse(
                    call: Call<TokenResponse>,
                    response: Response<TokenResponse>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                    onResult(null)
                }
            })
    }
}