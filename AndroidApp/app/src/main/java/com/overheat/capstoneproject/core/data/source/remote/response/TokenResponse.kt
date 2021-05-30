package com.overheat.capstoneproject.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class TokenResponse(

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("user_id")
    val userId: Int,

    @field:SerializedName("token")
    val token: String,

    @field:SerializedName("is_valid")
    val isValid: Boolean,

    @field:SerializedName("date_added")
    val dateAdded: String
)
