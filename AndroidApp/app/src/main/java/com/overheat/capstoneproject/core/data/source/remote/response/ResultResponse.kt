package com.overheat.capstoneproject.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ResultResponse(

    @field:SerializedName("cancer_proba")
    val cancerProba: Double,

    @field:SerializedName("email")
    val email: String?,

    @field:SerializedName("name")
    val name: String?,

    @field:SerializedName("user_id")
    val userId: Int?
)