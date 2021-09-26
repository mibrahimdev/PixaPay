package io.github.mohamedisoliman.pixapay.ui.search

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import io.github.mohamedisoliman.pixapay.ui.common.isPortrait


@Preview
@Composable
fun PreviewSearch() {
    SearchScreen()
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchScreen() {

    val viewModel: SearchImagesViewModel = viewModel()
    val currentConfig = LocalConfiguration.current
    val width = currentConfig.screenHeightDp.dp
    Column(
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchTopbar()

        LazyVerticalGrid(
            modifier = Modifier,
            cells = GridCells.Fixed(if (currentConfig.isPortrait()) 1 else 2),
            content = {
                itemsIndexed(viewModel.testImages) { _, item -> ImageCard(image = item) }
            }
        )
    }
}


@Composable
fun SearchTopbar() {

}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ImageCard(
    modifier: Modifier = Modifier,
    image: ImageUiModel,
) {
    Box(modifier = modifier
        .fillMaxWidth()
        .background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color.Transparent,
                    Color.Black
                ),
                startY = 100f
            )
        )
        .height(300.dp),
        contentAlignment = Alignment.BottomStart
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize(),
            painter = rememberImagePainter(image.url),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )

        ImageChips(image = image)
    }
}

@Composable
fun UserChip(userName: String) {
    Row(
        modifier = Modifier.wrapContentSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            imageVector = Icons.Outlined.Person,
            contentDescription = "",
            tint = Color.White
        )
        Text(
            text = userName,
            style = MaterialTheme.typography.caption.copy(color = Color.White),
        )
    }
}

@Composable
private fun ImageChips(
    modifier: Modifier = Modifier,
    image: ImageUiModel,
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(brush = Brush.verticalGradient(
                colors = listOf(
                    Color.Transparent,
                    Color.Black
                ),
                startY = 100f
            ))
            .padding(start = 16.dp, end = 16.dp),
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(bottom = 16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            UserChip(image.userName)
            LazyRow(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                content = {
                    itemsIndexed(image.tags) { _, item ->
                        Chip(tagName = item)
                    }
                }
            )
        }
    }
}

@Composable
fun Chip(
    modifier: Modifier = Modifier,
    tagName: String,
) {
    Text(
        text = tagName,
        style = MaterialTheme.typography.caption.copy(color = Color.White)
    )
}
