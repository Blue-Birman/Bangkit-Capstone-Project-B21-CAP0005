package com.overheat.capstoneproject.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListFaqResponse(

    @field:SerializedName("data")
    val data: List<FaqResponse>
)