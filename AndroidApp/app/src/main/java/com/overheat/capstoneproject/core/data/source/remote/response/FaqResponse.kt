package com.overheat.capstoneproject.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class FaqResponse(

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("question")
    val question: String,

    @field:SerializedName("answer")
    val answer: String
)