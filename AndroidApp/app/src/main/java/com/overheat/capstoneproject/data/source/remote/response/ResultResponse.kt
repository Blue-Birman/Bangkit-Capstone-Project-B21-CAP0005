package com.overheat.capstoneproject.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ResultResponse(

    @field:SerializedName("Id")
    val id: Int,

    @field:SerializedName("Image")
    val image: String,

    @field:SerializedName("CancerProba")
    val cancerProba: Float,

    @field:SerializedName("User_id")
    val userId: Int,

    @field:SerializedName("Date_added")
    val dateAdded: String
)