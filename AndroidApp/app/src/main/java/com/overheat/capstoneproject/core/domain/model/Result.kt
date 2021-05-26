package com.overheat.capstoneproject.core.domain.model

data class Result(
    val id: Int,
    val image: String,
    val cancerProba: Double,
    val userId: Int,
    val dateAddedval: String
)