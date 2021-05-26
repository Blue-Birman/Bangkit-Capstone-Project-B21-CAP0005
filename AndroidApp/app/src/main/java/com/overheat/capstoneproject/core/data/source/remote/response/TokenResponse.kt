package com.overheat.capstoneproject.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class TokenResponse(

    @field:SerializedName("Id")
    val id: Int,

    @field:SerializedName("User_id")
    val userId: Int,

    @field:SerializedName("token")
    val token: String,

    @field:SerializedName("is_valid")
    val isValid: Boolean,

    @field:SerializedName("Date_added")
    val dateAdded: String
)
