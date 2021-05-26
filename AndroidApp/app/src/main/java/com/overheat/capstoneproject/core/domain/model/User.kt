package com.overheat.capstoneproject.core.domain.model

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val passHash: String,
    val dateAdded: String
)