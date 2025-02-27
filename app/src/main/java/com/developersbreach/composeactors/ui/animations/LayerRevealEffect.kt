package com.developersbreach.composeactors.ui.animations

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import com.developersbreach.composeactors.ui.components.LoadNetworkImage

/**
 * Used in MovieDetailsScreen as background image overlay with reveal effect.
 */
@Composable
fun LayerRevealImage(
    poster: String?,
    isLayerRevealAnimationEnded: MutableState<Boolean>
) {
    // To animate the canvas which has two rectangles.
    val animateShape = remember { Animatable(1f) }.also {
        LaunchAnimation(it)
    }

    if (!animateShape.isRunning && animateShape.targetValue == 0f) {
        isLayerRevealAnimationEnded.value = true
    }

    val drawLayerColor = MaterialTheme.colors.background
    val imageLayerAlpha = if (isLayerRevealAnimationEnded.value) 0.35f else 1f

    LoadNetworkImage(
        imageUrl = poster.toString(),
        contentDescription = "",
        shape = RectangleShape,
        showAnimProgress = false,
        modifier = Modifier
            .fillMaxSize()
            .alpha(imageLayerAlpha)
    )

    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {
        // Draws a rectangle in canvas filling top half of the screen
        drawRevealingRectangle(this, animateShape, drawLayerColor)

        // Draws a rectangle in canvas filling bottom half of the screen
        // Rotate to 180 degrees so that desired reveal effect happens.
        this.rotate(180f) {
            drawRevealingRectangle(this, animateShape, drawLayerColor)
        }
    }
}

/**
 * @param canvasScope to determine the size occupied in canvas.
 * @param animateShape shape which will be animated in canvas
 */
private fun DrawScope.drawRevealingRectangle(
    canvasScope: DrawScope,
    animateShape: Animatable<Float, AnimationVector1D>,
    drawLayerColor: Color
) {
    drawRect(
        color = drawLayerColor,
        topLeft = Offset(0f, 0f),
        size = Size(
            width = canvasScope.size.width,
            height = canvasScope.size.height / 2 * animateShape.value
        ),
        alpha = 1f
        // Color, Luminosity, Overlay, Hue, ColorBurn (better fit)
        // blendMode = BlendMode.Hue
    )
}

/**
 * Launches a coroutine in composable to start animation with initial value.
 */
@Composable
private fun LaunchAnimation(
    animateShape: Animatable<Float, AnimationVector1D>
) {
    LaunchedEffect(animateShape) {
        animateShape.animateTo(
            targetValue = 0f,
            animationSpec = repeatable(
                animation = tween(
                    durationMillis = 1200,
                    easing = LinearEasing,
                    delayMillis = 100
                ),
                repeatMode = RepeatMode.Restart,
                iterations = 1
            )
        )
    }
}