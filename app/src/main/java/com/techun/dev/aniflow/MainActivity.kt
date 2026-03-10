package com.techun.dev.aniflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.techun.dev.aniflow.main.MainScreen
import com.techun.dev.aniflow.home.domain.model.Article
import com.techun.dev.aniflow.ui.theme.AniFlowTheme

class MainActivity : ComponentActivity() {
    private val sampleArticle = Article(
        id = 1,
        title = "Tsue to Tsurugi no Wistoria Season 2 Reveals Main Promo & Theme Songs",
        summary = "The official website revealed additional staff, theme songs, and the main promotional video. Premieres April 12.",
        content = "Contenido completo del artículo...",
        pubDate = "Thu, 05 Mar 2026",
        timeAgo = "2h ago",
        imageUrl = "https://cdn.myanimelist.net/s/common/uploaded_files/1772726642-0c37d2dbcb5ea95e4511f89ced08721d.jpeg",
        tag = "Season 2",
        source = "MyAnimeList",
        link = "https://myanimelist.net/news/73937632",
        isFavorite = false
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AniFlowTheme {
                MainScreen()
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    var isFav by remember { mutableStateOf(false) }
//
//                    Column(
//                        modifier = Modifier
//                            .padding(innerPadding)
//                            .padding(16.dp),
//                        verticalArrangement = Arrangement.spacedBy(16.dp)
//                    ) {
//                        AniFlowCardFeed(
//                            article = sampleArticle.copy(isFavorite = isFav),
//                            featured = true,
//                            onClick = {},
//                            onToggleFav = { isFav = !isFav } // ← toggle real
//                        )
//
//                        AniFlowCardFeed(
//                            article = sampleArticle.copy(isFavorite = isFav),
//                            featured = false,
//                            onClick = {},
//                            onToggleFav = { isFav = !isFav } // ← toggle real
//                        )
//                    }
//                }
            }
        }
    }
}

