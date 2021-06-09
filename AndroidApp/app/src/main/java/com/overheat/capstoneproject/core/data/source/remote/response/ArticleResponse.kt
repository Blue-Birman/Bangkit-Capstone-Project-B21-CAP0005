package com.overheat.capstoneproject.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ArticleResponse(

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("image")
    val image: String?,

    @field:SerializedName("article")
    val article: String?
)
