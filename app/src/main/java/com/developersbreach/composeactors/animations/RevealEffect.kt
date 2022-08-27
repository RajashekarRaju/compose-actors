package com.developersbreach.composeactors.animations

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * @return Reusable modifier function, shows reveal effect animation on applied composables.
 * @initialState higher the value, more width will be animated.
 */
fun Modifier.borderRevealAnimation(
    initialState: Float = 28f,
    size: Dp = 60.dp,
    shape: Shape = CircleShape,
): Modifier = composed {

    val animate = remember { Animatable(initialState) }.apply {
        RevealEffectAnimation(this)
    }

    this
        .size(size)
        .clip(shape)
        .background(color = MaterialTheme.colors.surface)
        .border(
            width = Dp(animate.value),
            color = MaterialTheme.colors.onSurface,
            shape = shape,
        )
}

/**
 * @param animateShape shape which needs to be animated.
 * Animates between float 28f to 1f as of now for default args.
 */
@Composable
private fun RevealEffectAnimation(
    animateShape: Animatable<Float, AnimationVector1D>,
    targetValue: Float = 1f,
    durationMillis: Int = 1000,
    delayMillis: Int = 250,
    iterations: Int = 1
) {
    LaunchedEffect(animateShape) {
        animateShape.animateTo(
            targetValue = targetValue,
            animationSpec = repeatable(
                animation = tween(
                    durationMillis = durationMillis,
                    easing = LinearEasing,
                    delayMillis = delayMillis
                ),
                repeatMode = RepeatMode.Restart,
                iterations = iterations
            )
        )
    }
}