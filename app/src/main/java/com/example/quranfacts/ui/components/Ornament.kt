package com.example.quranfacts.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

/**
 * The eight-pointed star of Islamic geometry (Rub el Hizb): two overlapping squares.
 * Vertices alternate between the outer radius and the point where the squares cross.
 */
fun starPath(cx: Float, cy: Float, outer: Float, rotationDeg: Float = -90f): Path {
    val inner = outer * cos(PI / 4).toFloat() / cos(PI / 8).toFloat()
    val path = Path()
    for (k in 0 until 16) {
        val angle = Math.toRadians((rotationDeg + k * 22.5f).toDouble())
        val r = if (k % 2 == 0) outer else inner
        val x = cx + (r * cos(angle)).toFloat()
        val y = cy + (r * sin(angle)).toFloat()
        if (k == 0) path.moveTo(x, y) else path.lineTo(x, y)
    }
    path.close()
    return path
}

@Composable
fun StarOrnament(
    size: Dp,
    color: Color,
    modifier: Modifier = Modifier,
    outlined: Boolean = false,
) {
    Canvas(modifier.size(size)) {
        val c = Offset(this.size.width / 2, this.size.height / 2)
        val path = starPath(c.x, c.y, this.size.minDimension / 2)
        if (outlined) {
            drawPath(path, color, style = Stroke(width = 1.4.dp.toPx()))
            drawPath(starPath(c.x, c.y, this.size.minDimension / 4, -67.5f), color, style = Stroke(width = 1.dp.toPx()))
        } else {
            drawPath(path, color)
        }
    }
}

@Composable
fun OrnamentDivider(color: Color, modifier: Modifier = Modifier) {
    Row(
        modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Canvas(Modifier.weight(1f).height(1.dp)) {
            drawLine(
                Brush.horizontalGradient(listOf(Color.Transparent, color.copy(alpha = 0.6f))),
                Offset(0f, 0f), Offset(size.width, 0f), strokeWidth = 1.dp.toPx(),
            )
        }
        StarOrnament(10.dp, color, Modifier.padding(horizontal = 10.dp))
        Canvas(Modifier.weight(1f).height(1.dp)) {
            drawLine(
                Brush.horizontalGradient(listOf(color.copy(alpha = 0.6f), Color.Transparent)),
                Offset(0f, 0f), Offset(size.width, 0f), strokeWidth = 1.dp.toPx(),
            )
        }
    }
}

private class Star(val x: Float, val y: Float, val radius: Float, val phase: Float, val freq: Int, val base: Float)

/** A gently twinkling night sky. Frequencies are integers so the loop is seamless. */
@Composable
fun Starfield(
    modifier: Modifier = Modifier,
    count: Int = 110,
    color: Color = Color.White,
    seed: Int = 7,
) {
    val stars = androidx.compose.runtime.remember(count, seed) {
        val rnd = Random(seed)
        List(count) {
            Star(
                x = rnd.nextFloat(), y = rnd.nextFloat(),
                radius = 0.5f + rnd.nextFloat() * 1.5f,
                phase = rnd.nextFloat() * 6.2831f,
                freq = 1 + rnd.nextInt(3),
                base = 0.3f + rnd.nextFloat() * 0.65f,
            )
        }
    }
    val transition = rememberInfiniteTransition(label = "stars")
    val t by transition.animateFloat(
        initialValue = 0f,
        targetValue = (2 * PI).toFloat(),
        animationSpec = infiniteRepeatable(tween(9000, easing = LinearEasing), RepeatMode.Restart),
        label = "twinkle",
    )
    Canvas(modifier) {
        for (s in stars) {
            val twinkle = 0.55f + 0.45f * sin(t * s.freq + s.phase)
            drawCircle(
                color.copy(alpha = (s.base * twinkle).coerceIn(0f, 1f)),
                radius = s.radius * density,
                center = Offset(s.x * size.width, s.y * size.height),
            )
        }
    }
}
