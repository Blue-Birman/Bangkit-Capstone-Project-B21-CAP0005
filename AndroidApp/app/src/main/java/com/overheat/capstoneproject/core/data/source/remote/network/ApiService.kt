package com.overheat.capstoneproject.core.data.source.remote.network

import com.overheat.capstoneproject.core.data.source.remote.response.*
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    // Articles
    @GET("articles")
    suspend fun getListArticles() : List<ArticleResponse>

    @POST("article")
    fun getDetailArticles(
        @Body requestBody: RequestBody
    ) : Call<DetailArticleResponse>

    // Comment
    @POST("comment")
    suspend fun postComment(
        @Body requestBody: RequestBody
    )

    // Diagnose
    @GET("diagnose")
    suspend fun getHistoryDiagnose(
        @Body requestBody: RequestBody
    ) : List<DiagnoseResponse>

    @POST("diagnose")
    fun postImage(
        @Body requestBody: RequestBody
    ) : ResultResponse

    // Result
    @GET("retrieve_result")
    fun getDiagnoseResult(
        @Body requestBody: RequestBody
    ) : DiagnoseResponse

    // Login
    @POST("login")
    fun postLogin(
        @Body requestBody: RequestBody
    ) : TokenResponse

    // User
    @POST("user")
    fun postNewUser(
        @Body requestBody: RequestBody
    ) : UserResponse

    // Logout
    @POST("logout")
    fun postLogout(
        @Body requestBody: RequestBody
    ) : TokenResponse
}