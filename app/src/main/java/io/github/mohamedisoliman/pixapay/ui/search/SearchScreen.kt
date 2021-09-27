package io.github.mohamedisoliman.pixapay.ui.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import io.github.mohamedisoliman.pixapay.ui.common.ImageChips
import io.github.mohamedisoliman.pixapay.ui.common.isPortrait


@Preview
@Composable
fun PreviewSearch() {
    SearchScreen(PreviewData.images) {}
}

@Composable
fun SearchScreen(images: List<ImageUiModel>, onImageClicked: (Long) -> Unit) {

    Box(
        modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        ImageListView(images, onImageClicked)
        SearchTopbar()

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ImageListView(
    images: List<ImageUiModel>,
    onImageClicked: (Long) -> Unit,
) {
    val currentConfig = LocalConfiguration.current
    LazyVerticalGrid(
        contentPadding = PaddingValues(top = TextFieldDefaults.MinHeight + 16.dp),
        modifier = Modifier,
        cells = GridCells.Fixed(if (currentConfig.isPortrait()) 2 else 4),
        content = {
            itemsIndexed(images) { _, item ->
                ImageCard(
                    modifier = Modifier.padding(8.dp),
                    image = item,
                    onImageClicked = onImageClicked
                )
            }
        }
    )
}


@Composable
fun SearchTopbar(modifier: Modifier = Modifier) {
    var textState by remember { mutableStateOf("") }
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 8.dp),
        value = textState,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.surface,
            cursorColor = MaterialTheme.colors.onSurface,
            disabledLabelColor = MaterialTheme.colors.surface,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        onValueChange = {
            textState = it
        },
        shape = RoundedCornerShape(8.dp),
        singleLine = true,
        trailingIcon = {
            IconButton(onClick = {
                // TODO:
            }) {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    tint = MaterialTheme.colors.onSurface,
                    contentDescription = ""
                )
            }
        }
    )
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ImageCard(
    modifier: Modifier = Modifier,
    image: ImageUiModel,
    showExtraChips: Boolean = false,
    onImageClicked: (Long) -> Unit,
) {
    Box(modifier = modifier
        .fillMaxWidth()
        .height(200.dp)
        .clip(RoundedCornerShape(16.dp))
        .clickable { onImageClicked(image.imageId) },
        contentAlignment = Alignment.BottomStart
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = rememberImagePainter(image.url),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )

        ImageChips(image = image, showExtraChips = showExtraChips)
    }
}

@Composable
fun IconChip(icon: ImageVector, text: String) {
    Row(
        modifier = Modifier.wrapContentSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "",
            tint = Color.White
        )
        Text(
            text = text,
            style = MaterialTheme.typography.caption.copy(color = Color.White),
        )
    }
}