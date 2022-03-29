package com.developersbreach.composeactors.utils

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.developersbreach.composeactors.R
import com.developersbreach.composeactors.ui.theme.ComposeActorsTheme

/**
 * Not used anywhere in app right now.
 * Made only to use as an example in article.
 */
@Composable
fun LayerRevealImage() {
    ComposeActorsTheme(
        darkTheme = true
    ) {
        Surface(
            color = MaterialTheme.colors.background,
        ) {
            // Since I'm using insets, including these padding will adjust image bounds.
            Spacer(
                Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .navigationBarsPadding()
            )

            Image(
                painterResource(id = R.drawable.adele),
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // To animate the canvas which had two rectangles.
            val animateShape = remember { Animatable(1f) }.also {
                LaunchAnimation(it)
            }

            Canvas(
                modifier = Modifier.fillMaxSize()
            ) {
                // Draws a rectangle in canvas filling top half of the screen
                drawRevealingRectangle(this, animateShape)

                // Draws a rectangle in canvas filling bottom half of the screen
                // Rotate to 180 degrees so that desired reveal effect happens.
                this.rotate(180f) {
                    drawRevealingRectangle(this, animateShape)
                }
            }
        }
    }
}

/**
 * @param canvasScope to determine the size occupied in canvas.
 * @param animateShape shape which will be animated in canvas
 */
fun DrawScope.drawRevealingRectangle(
    canvasScope: DrawScope,
    animateShape: Animatable<Float, AnimationVector1D>
) {
    drawRect(
        color = Color.LightGray,
        topLeft = Offset(0f, 0f),
        size = Size(
            width = canvasScope.size.width,
            height = canvasScope.size.height / 2 * animateShape.value
        ),
        alpha = 1f,
        // Color, Luminosity, Overlay, Hue, ColorBurn
        blendMode = BlendMode.Hue
    )
}

/**
 * Launches a coroutine in composable to start animation with initial value.
 */
@Composable
fun LaunchAnimation(
    animateShape: Animatable<Float, AnimationVector1D>
) {
    LaunchedEffect(animateShape) {
        animateShape.animateTo(
            targetValue = 0f,
            animationSpec = repeatable(
                animation = tween(
                    durationMillis = 1500,
                    easing = FastOutSlowInEasing,
                    delayMillis = 100
                ),
                repeatMode = RepeatMode.Restart,
                iterations = 1
            )
        )
    }
}