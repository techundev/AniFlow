package com.techun.dev.aniflow.core.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil3.compose.AsyncImage
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.request.error
import coil3.request.placeholder
import com.techun.dev.aniflow.R

@Composable
fun AniFlowAsyncImage(
    modifier: Modifier = Modifier,
    data: Any,
    contentDescription: String? = null,
    drawablePlaceHolder: Int = R.drawable.logo_splash_screen,
    drawableError: Int =R.drawable.logo_splash_screen,
    crossFadeDurationMillis: Int = 500,
    contentScale: ContentScale = ContentScale.Fit,
) {
    AsyncImage(
        model = ImageRequest
            .Builder(LocalContext.current)
            .data(data)
            .crossfade(true)
            .crossfade(crossFadeDurationMillis)
            .diskCachePolicy(CachePolicy.ENABLED)
            .networkCachePolicy(CachePolicy.ENABLED)
            .placeholder(drawablePlaceHolder)
            .error(drawableError)
            .build(),

        contentDescription = contentDescription,
        contentScale = contentScale,
        modifier = modifier
    )
}