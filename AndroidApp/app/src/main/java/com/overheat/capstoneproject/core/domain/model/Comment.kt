package com.overheat.capstoneproject.core.domain.model

data class Comment(
    val id: Int,
    val articleId: Int,
    val userId: Int,
    val comment: String,
    val dateAdded: String
)