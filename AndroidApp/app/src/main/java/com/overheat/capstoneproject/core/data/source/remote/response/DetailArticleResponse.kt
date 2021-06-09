package com.overheat.capstoneproject.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class DetailArticleResponse(

    @field:SerializedName("article")
    val article: ArticleResponse,

    @field:SerializedName("comments")
    val comments: List<CommentResponse>
)
