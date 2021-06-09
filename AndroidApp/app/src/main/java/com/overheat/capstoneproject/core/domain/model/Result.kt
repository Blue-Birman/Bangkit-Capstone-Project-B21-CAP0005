package com.overheat.capstoneproject.core.domain.model

data class Result(
    val cancerProba: Double,
    val email: String?,
    val name: String?,
    val userId: Int?
)