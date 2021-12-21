package com.developersbreach.composeactors.ui.search

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke

/**
 * @param animateShape shape which will be animated infinitely.
 * @param targetValue final float state to be animated.
 * @param durationMillis duration took for animating once.
 */
@Composable
private fun AnimateShapeInfinitely(
    animateShape: Animatable<Float, AnimationVector1D>,
    targetValue: Float = 1f,
    durationMillis: Int = 1000
) {
    LaunchedEffect(animateShape) {
        animateShape.animateTo(
            targetValue = targetValue,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = durationMillis,
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Restart
            )
        )
    }
}

/**
 * Animation looks like a search icon infinitely framing itself.
 *
 * animateCircle property -> animates circle in search.
 * animateLine property   -> animates floating/growing line and joins circle to form search
 * looking icon at the end.
 */
@Composable
fun AnimatedSearch() {

    // Simple progressive circle looking animation
    val animateCircle = remember { Animatable(0f) }.apply {
        AnimateShapeInfinitely(this)
    }

    // 0.6f for initial value to reduce floating time of line to reach it's final state.
    // Settings it to 0f -> final animation output looks kind of aggressive movements.
    val animateLine = remember { Animatable(0.6f) }.apply {
        AnimateShapeInfinitely(this)
    }

    // Appears different for dark/light theme colors.
    val surfaceColor = MaterialTheme.colors.surface

    // Arcs & Line drawn in canvas at animation final state looks like search icon.
    Canvas(
        modifier = Modifier
    ) {
        drawArc(
            color = surfaceColor,
            startAngle = 45f,
            sweepAngle = 360f * animateCircle.value,
            useCenter = false,
            size = Size(80f, 80f),
            style = Stroke(16f, cap = StrokeCap.Round)
        )

        drawLine(
            color = surfaceColor,
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