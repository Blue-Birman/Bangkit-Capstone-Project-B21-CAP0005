package com.overheat.capstoneproject.core.domain.model

data class Diagnose(
    val cancerProba: Double,
    val dateAdded: String,
    val id: Int,
    val image: String?,
    val userId: Int?
)
