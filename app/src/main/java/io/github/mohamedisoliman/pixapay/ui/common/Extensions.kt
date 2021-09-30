package io.github.mohamedisoliman.pixapay.ui.common

import android.content.res.Configuration
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val verticalBrush = Brush.verticalGradient(
    colors = listOf(
        Color.Transparent,
        Color.Black
    ),
    startY = 30f
)

fun Configuration.isPortrait(): Boolean = orientation == Configuration.ORIENTATION_PORTRAIT

fun Configuration.isLandScape(): Boolean = orientation == Configuration.ORIENTATION_LANDSCAPE