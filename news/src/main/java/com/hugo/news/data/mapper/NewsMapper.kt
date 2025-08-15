package com.hugo.news.data.mapper

import com.hugo.news.data.repository.dto.NewsDto
import com.hugo.news.domain.model.NewsInfo


fun NewsDto.toF1NewsArticles(): List<NewsInfo> {
    return articles.orEmpty().mapNotNull { article ->
        val id = article.id ?: return@mapNotNull null
        val headline = article.headline ?: return@mapNotNull null
        val webUrl = article.links?.web?.href ?: return@mapNotNull null

        val imageUrl = article.images
            ?.firstOrNull { it.type == "header" }
            ?.url ?: article.images?.firstOrNull()?.url

        NewsInfo(
            id = id,
            headline = headline,
            description = article.description,
            byline = article.byline,
            published = article.published ?: article.lastModified,
            imageUrl = imageUrl,
            webUrl = webUrl
        )
    }
}
