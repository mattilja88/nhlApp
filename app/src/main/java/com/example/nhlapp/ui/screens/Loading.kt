package com.example.nhlapp.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.animation.core.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.nhlapp.R

@Composable
fun LoadingScreen() {
    val pulseTransition = rememberInfiniteTransition()
    val scale by pulseTransition.animateFloat(
        initialValue = 0.8f, targetValue = 1.2f,
        animationSpec = infiniteRepeatable(animation = tween(700, easing = FastOutSlowInEasing), repeatMode = RepeatMode.Reverse)
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Surface(
            modifier = Modifier.size(50.dp * scale),
            shape = CircleShape,
            color = Color.Gray.copy(alpha = 0.5f)
        ) {}
        Text(stringResource(R.string.ladataan))
    }
}


