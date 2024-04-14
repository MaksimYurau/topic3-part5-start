package com.topic3.android.reddit.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.maksimyurau.android.lab11_reddit.components.ImagePost
import com.maksimyurau.android.lab11_reddit.components.JoinedToast
import com.maksimyurau.android.lab11_reddit.components.TextPost
import com.maksimyurau.android.lab11_reddit.model.PostModel
import com.maksimyurau.android.lab11_reddit.model.PostType
import com.maksimyurau.android.lab11_reddit.viewmodel.MainViewModel
import java.util.Timer
import kotlin.concurrent.schedule

@Composable
fun HomeScreen(viewModel: MainViewModel) {
    val posts: List<PostModel>
            by viewModel.allPosts.observeAsState(listOf())
    var isToastVisible by remember { mutableStateOf(false) }
    val onJoinClickAction: (Boolean) -> Unit = { joined ->
        isToastVisible = joined
        if (isToastVisible) {
            Timer().schedule(3000) { isToastVisible = false }
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.background(
                color =
                MaterialTheme.colors.secondary
            )
        ) {
            items(posts) {
                if (it.type == PostType.TEXT) {
                    TextPost(it, onJoinButtonClick = onJoinClickAction)
                } else {
                    ImagePost(it, onJoinButtonClick = onJoinClickAction)
                }
                Spacer(modifier = Modifier.height(6.dp))
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        ) {
            JoinedToast(visible = isToastVisible)
        }
    }
}
