package com.overheat.capstoneproject.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class CommentResponse(

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("article_id")
    val articleId: Int,

    @field:SerializedName("user_id")
    val userId: Int,

    @field:SerializedName("comment")
    val comment: String,

    @field:SerializedName("date_added")
    val dateAdded: String
)