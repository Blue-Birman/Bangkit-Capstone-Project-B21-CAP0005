package com.overheat.capstoneproject.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ArticleResponse(

    @field:SerializedName("Id")
    val id: Int,

    @field:SerializedName("Title")
    val title: String,

    @field:SerializedName("Image")
    val image: String,

    @field:SerializedName("Article")
    val article: String
)
