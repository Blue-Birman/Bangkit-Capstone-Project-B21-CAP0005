package com.overheat.capstoneproject.core.domain.model

data class History(
    val id: Int,
    val userId: Int,
    val activity: String,
    val dateAdded: String
)