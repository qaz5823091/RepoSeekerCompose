package com.example.reposeekercompose

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {
    @GET("")
    suspend fun getRepo(q: String): GithubRepository

    @GET("search/repositories")
    suspend fun search(@Query("q") q: String): GithubSearchResult

    companion object {
        var githubService: GithubService? = null
        fun getInstance(): GithubService {
            if (githubService == null) {
                githubService = Retrofit.Builder()
                    .baseUrl(Config.baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(GithubService::class.java)
            }

            return githubService!!
        }
    }
}