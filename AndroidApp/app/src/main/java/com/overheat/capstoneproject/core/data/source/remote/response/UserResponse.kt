package com.overheat.capstoneproject.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class UserResponse(

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("pass_hash")
    val passHash: String,

    @field:SerializedName("date_added")
    val dateAdded: String
)
