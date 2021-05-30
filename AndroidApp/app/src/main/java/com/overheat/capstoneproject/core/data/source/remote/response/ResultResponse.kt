package com.overheat.capstoneproject.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ResultResponse(
    @field:SerializedName("cancer_proba")
    val cancerProba: Double
)