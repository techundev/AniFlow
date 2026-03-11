package com.techun.dev.aniflow.core.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun AniFlowButton(
    text: String,
    onclick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = MaterialTheme.shapes.extraLarge,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = if (enabled) 8.dp else 0.dp,
                shape = MaterialTheme.shapes.extraLarge,
                ambientColor = containerColor,
                spotColor = containerColor
            ),
        onClick = onclick,
        enabled = enabled,
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
            disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 4.dp,
            pressedElevation = 0.dp
        )
    ) {
        AniFlowText(
            text = text.uppercase(),
            modifier = Modifier.padding(vertical = 8.dp),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.surface,
            fontWeight = FontWeight.Bold
        )
    }
}