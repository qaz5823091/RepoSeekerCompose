package com.example.reposeekercompose

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.net.URLEncoder

class GithubRepositoryViewModel: ViewModel() {
    lateinit var specificRepoReponse: GithubRepository
    var repoListResponse: List<GithubRepository> by mutableStateOf(listOf())
    var errorMessage: String by mutableStateOf("")

    fun getRepo(url: String) {
        viewModelScope.launch {
            val githubService = GithubService.getInstance()
            try {
                specificRepoReponse = githubService.getRepo(url)
            }
            catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    fun getRepoList(query: String) {
        viewModelScope.launch {
            val githubService = GithubService.getInstance()
            try {
                val encodedQuery = URLEncoder.encode(query, "UTF-8")
                val repoList = githubService.search(encodedQuery)
                repoListResponse = repoList.items
                Log.d("try", repoList.items.toString())
            }
            catch (e: Exception) {
                errorMessage = e.message.toString()
                Log.d("try", "fail")
            }
        }
    }
}