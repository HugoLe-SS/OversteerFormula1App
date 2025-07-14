package com.hugo.news.domain.model

data class NewsInfo(
    val id: Long,
    val headline: String,
    val description: String?,
    val byline: String?,
    val published: String?,
    val imageUrl: String?,
    val webUrl: String
)
