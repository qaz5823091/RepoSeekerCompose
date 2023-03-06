package com.example.reposeekercompose

data class GithubSearchResult(
    val total_count: Int,
    val items: List<GithubRepository>
)

data class GithubRepository(
    val id: Int,
    val name: String,
    val full_name: String,
    val owner: GithubUser,
    val url: String,
    val html_url: String,
    val description: String,
    val stargazers_count: Int,
    val language: String
)

data class GithubUser(
    val login: String,
    val avatar_url: String,
    val html_url: String
)