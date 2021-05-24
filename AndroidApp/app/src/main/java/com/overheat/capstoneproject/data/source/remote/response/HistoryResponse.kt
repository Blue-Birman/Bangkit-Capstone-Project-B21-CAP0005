package com.overheat.capstoneproject.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class HistoryResponse(

    @field:SerializedName("Id")
    val id: Int,

    @field:SerializedName("User_id")
    val userId: Int,

    @field:SerializedName("Activity")
    val activity: String,

    @field:SerializedName("Date_added")
    val dateAdded: String
)
