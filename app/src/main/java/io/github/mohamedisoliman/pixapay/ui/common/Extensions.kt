package io.github.mohamedisoliman.pixapay.ui.common

import android.content.res.Configuration
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

fun Configuration.isPortrait(): Boolean = orientation == Configuration.ORIENTATION_PORTRAIT

fun Configuration.isLandScape(): Boolean = orientation == Configuration.ORIENTATION_LANDSCAPE