package io.github.mohamedisoliman.pixapay.ui.image_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.coil.rememberCoilPainter
import io.github.mohamedisoliman.pixapay.ui.common.ImageChips
import io.github.mohamedisoliman.pixapay.ui.search.ImageUiModel
import io.github.mohamedisoliman.pixapay.ui.search.PreviewData


@Preview
@Composable
fun PreviewImageDetails() {
    ImageDetailsScreen(PreviewData.image)
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ImageDetailsScreen(image: ImageUiModel) {

    var scale by remember { mutableStateOf(1f) }
    val rotationState = remember { mutableStateOf(1f) }

    Box(
        modifier = Modifier
            .background(color = Color.Transparent)
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTransformGestures { _, _, zoom, rotation ->
                    scale *= zoom
                    rotationState.value += rotation
                }
            },
    ) {
        Image(
            modifier = Modifier
                .align(Alignment.Center)
                .graphicsLayer(
                    scaleX = maxOf(.5f, minOf(3f, scale)),
                    scaleY = maxOf(.5f, minOf(3f, scale)),
                    rotationZ = rotationState.value
                ),
            painter = rememberCoilPainter(request = image.largeImageURL),
            contentDescription = ""
        )
        ImageChips(
            modifier = Modifier.align(Alignment.BottomCenter),
            image = image,
            showExtraChips = true
        )
    }


}