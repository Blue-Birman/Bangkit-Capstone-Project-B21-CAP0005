package com.overheat.capstoneproject.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class CommentResponse(

    @field:SerializedName("ID")
    val id: Int,

    @field:SerializedName("Article_id")
    val articleId: Int,

    @field:SerializedName("User_id")
    val userId: Int,

    @field:SerializedName("Comment")
    val comment: String,

    @field:SerializedName("date_added")
    val dateAdded: String
)