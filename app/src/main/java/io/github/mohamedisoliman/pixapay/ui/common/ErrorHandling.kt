package io.github.mohamedisoliman.pixapay.ui.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.WifiOff
import io.github.mohamedisoliman.pixapay.R
import java.net.UnknownHostException

fun Throwable.toUiModel(): ErrorUiModel = when (this) {
    is UnknownHostException -> ErrorUiModel(
        errorMessageId = R.string.network_error_message,
        errorIconId = Icons.Outlined.WifiOff
    )
    else -> ErrorUiModel()
}