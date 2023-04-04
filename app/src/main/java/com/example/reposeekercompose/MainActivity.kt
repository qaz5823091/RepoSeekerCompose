package com.example.reposeekercompose

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.reposeekercompose.ui.theme.RepoSeekerComposeTheme

class MainActivity : ComponentActivity() {
    private val githubRepositoryViewModel by viewModels<GithubRepositoryViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RepoSeekerComposeTheme() {
                Surface {
                    MainScreen()
                }
            }
        }
    }

    @Composable
    fun MainScreen() {
        val textState = remember {
            mutableStateOf(TextFieldValue(""))
        }

        val expandedState = remember {
            mutableStateOf(false)
        }

        lateinit var selectedRepo: GithubRepository

        Column() {
            SearchBar(textState = textState)
            RepositoryList(
                repoList = githubRepositoryViewModel.repoListResponse
            )
            githubRepositoryViewModel.getRepoList(textState.value.text)
        }
        githubRepositoryViewModel.getRepo("url")
    }
}

fun customToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandableRepositoryItem(repo: GithubRepository,expandedState: MutableState<Boolean>) {
    if (expandedState.value) {
        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(24.dp, 24.dp, 24.dp, 24.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp,
            ),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
            ),
            onClick = {
                expandedState.value = !expandedState.value
            }
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier
                        .height(24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        repo.owner.login
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(repo.full_name, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(2.dp))
                Text(repo.description, color = Color.Black)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Star, null)
                    Text(repo.stargazers_count.toString())
                    Spacer(modifier = Modifier.width(10.dp))
                    Icon(Icons.Default.Info, null)
                    Text(repo.language)
                }
            }
        }
    }
}

@Composable
fun SearchBar(textState: MutableState<TextFieldValue>) {
    Row(
        modifier = Modifier
            .padding(24.dp, 10.dp, 24.dp, 10.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(28.dp))
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val focusManager = LocalFocusManager.current

        IconButton(onClick = {}) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = null
            )
        }
        BasicTextField(
            value = textState.value,
            onValueChange = { textState.value = it },
            modifier = Modifier
                .weight(1f)
        ) {
            if (textState.value.text.isEmpty()) {
                Text("請輸入關鍵字")
            }
            it()
        }
        IconButton(onClick = {}) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
            )
        }
    }
}

@Composable
fun RepositoryList(repoList: List<GithubRepository>) {
    var selectedItem by remember { mutableStateOf("") }
    LazyColumn(
        modifier = Modifier
            .padding(start = 24.dp, end = 24.dp)
    ) {
        itemsIndexed(items = repoList) { _, item ->
            RepositoryItem(
                repo = item,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun RepositoryItem(repo: GithubRepository) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(10.dp,5.dp,10.dp,10.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation =  10.dp,
        ),
        colors = CardDefaults.cardColors(
            containerColor =  MaterialTheme.colorScheme.primaryContainer,
        )
    ) {
        Column() {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier
                        .height(24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    GlideImage(
                        model = repo.owner.avatar_url,
                        contentDescription = null,
                        contentScale = ContentScale.FillHeight,
                        modifier = Modifier
                            .background(Color.White)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        repo.owner.login
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(repo.full_name, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(2.dp))
                repo.description ?.let { Text(repo.description, color = Color.Black) }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Star, null)
                    Text(repo.stargazers_count.toString())
                    Spacer(modifier = Modifier.width(10.dp))
                    repo.language ?.let {
                        Icon(Icons.Default.Info, null)
                        Text(repo.language)
                    }
                }
            }
        }
    }
}