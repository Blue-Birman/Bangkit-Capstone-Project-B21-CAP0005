package com.overheat.capstoneproject.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class HistoryResponse(

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("user_id")
    val userId: Int,

    @field:SerializedName("activity")
    val activity: String,

    @field:SerializedName("date_added")
    val dateAdded: String
)
