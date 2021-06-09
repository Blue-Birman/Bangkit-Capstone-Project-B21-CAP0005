package com.overheat.capstoneproject.core.domain.model

data class DetailArticle(
    val article: Article,
    val comments: List<Comment>
)
