package com.hugo.news.data.repository.dto

import com.google.gson.annotations.SerializedName

data class NewsDto(
    val header: String,
    val link: Link,
    val articles: List<Article>
)

data class Link(
    val language: String?,
    val rel: List<String>?,
    val href: String,
    val text: String?,
    val shortText: String?,
    val isExternal: Boolean?,
    val isPremium: Boolean?
)

data class Article(
    val id: Long,
    val nowId: String?,
    val contentKey: String?,
    val dataSourceIdentifier: String?,
    val type: String?,
    val headline: String,
    val description: String?,
    val lastModified: String?,
    val published: String?,
    val images: List<Image>?,
    val categories: List<Category>?,
    val premium: Boolean?,
    val links: ArticleLinksDto?,
    val byline: String?
)

data class Image(
    val dataSourceIdentifier: String?,
    val id: Long?,
    val type: String?,
    val name: String?,
    val credit: String?,
    val caption: String?,
    val alt: String?,
    val height: Int?,
    val width: Int?,
    val url: String
)

data class Category(
    val id: Long?,
    val type: String?,
    val guid: String?,
    val description: String?,
    val sportId: Int?,
    val topicId: Int?,
    val teamId: Int?,
    val leagueId: Int?,
    val athleteId: Int?,
    val team: Team?,
    val league: League?,
    val athlete: Athlete?
)

data class Team(
    val id: Long?,
    val description: String?
)

data class League(
    val id: Long?,
    val description: String?,
    val abbreviation: String?,
    val links: LeagueLinks?
)

data class LeagueLinks(
    val web: LeagueHref?,
    val mobile: LeagueHref?
)

data class LeagueHref(
    val leagues: Href?
)

data class Href(
    val href: String?
)

data class Athlete(
    val id: Long?,
    val description: String?,
    val links: AthleteLinksDto?
)

data class AthleteLinksDto(
    val web: AthleteHrefDto?,
    val mobile: AthleteHrefDto?
)

data class AthleteHrefDto(
    val athletes: Href?
)

data class ArticleLinksDto(
    val web: Href?,
    val mobile: Href?,
    val api: ApiLinksDto?,
    val app: AppLinksDto?
)

data class ApiLinksDto(
    val self: Href?
)

data class AppLinksDto(
    @SerializedName("sportscenter")
    val sportsCenter: Href?
)