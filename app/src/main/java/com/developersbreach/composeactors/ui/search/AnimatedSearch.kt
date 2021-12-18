package com.developersbreach.composeactors.ui.search

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke

@Composable
private fun AnimateShapeInfinitely(
    animateShape: Animatable<Float, AnimationVector1D>
) {
    LaunchedEffect(animateShape) {
        animateShape.animateTo(
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 1000,
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Restart
            )
        )
    }
}

@Composable
fun AnimatedSearch() {

    val animateCircle = remember { Animatable(0f) }
    val animateLine = remember { Animatable(0.6f) }
    AnimateShapeInfinitely(animateCircle)
    AnimateShapeInfinitely(animateLine)

    Canvas(
        modifier = Modifier
    ) {
        drawArc(
            color = Color(0xFF162129),
            startAngle = 45f,
            sweepAngle = 360f * animateCircle.value,
            useCenter = false,
            size = Size(80f, 80f),
            style = Stroke(16f, cap = StrokeCap.Round)
        )

        drawLine(
            color = Color(0xFF162129),
            strokeWidth = 16f,
            cap = StrokeCap.Round,
            start = Offset(
                animateLine.value * 80f,
                animateLine.value * 80f
            ),
            end = Offset(
                animateLine.value * 110f,
                animateLine.value * 110f
            )
        )
    }
}