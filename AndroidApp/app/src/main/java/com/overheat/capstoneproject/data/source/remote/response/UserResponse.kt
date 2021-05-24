package com.overheat.capstoneproject.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class UserResponse(

    @field:SerializedName("Id")
    val id: Int,

    @field:SerializedName("Name")
    val name: String,

    @field:SerializedName("Email")
    val email: String,

    @field:SerializedName("Pass_hash")
    val passHash: String,

    @field:SerializedName("Date_added")
    val dateAdded: String
)
