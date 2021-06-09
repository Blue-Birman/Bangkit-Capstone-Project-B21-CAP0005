package com.overheat.capstoneproject.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class DiagnoseResponse(

    @field:SerializedName("cancer_proba")
    val cancerProba: Double,

    @field:SerializedName("date_added")
    val dateAdded: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("image")
    val image: String?,

    @field:SerializedName("user_id")
    val userId: Int?
)
