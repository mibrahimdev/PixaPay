package io.github.mohamedisoliman.pixapay.ui.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Error
import androidx.compose.ui.graphics.vector.ImageVector
import io.github.mohamedisoliman.pixapay.R

data class ErrorUiModel(
    val errorMessageId: Int = R.string.error_message,
    val errorIconId: ImageVector = Icons.Outlined.Error,
)