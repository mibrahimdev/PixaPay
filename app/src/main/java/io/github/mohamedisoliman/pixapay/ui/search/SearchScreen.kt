package io.github.mohamedisoliman.pixapay.ui.search

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import io.github.mohamedisoliman.pixapay.R
import io.github.mohamedisoliman.pixapay.ui.common.isPortrait


@Preview
@Composable
fun PreviewSearch() {
    SearchScreen()
}

@Composable
fun SearchScreen() {

    val viewModel: SearchImagesViewModel = viewModel()
    Box(
        modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp),
        contentAlignment = Alignment.TopCenter
//        verticalArrangement = Arrangement.SpaceAround,
//        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ImageListView(viewModel)
        SearchTopbar()

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ImageListView(
    viewModel: SearchImagesViewModel,
) {
    val currentConfig = LocalConfiguration.current
    LazyVerticalGrid(
        contentPadding = PaddingValues(top = TextFieldDefaults.MinHeight + 16.dp),
        modifier = Modifier,
        cells = GridCells.Fixed(if (currentConfig.isPortrait()) 2 else 4),
        content = {
            itemsIndexed(viewModel.testImages) { _, item ->
                ImageCard(modifier = Modifier.padding(8.dp), image = item)
            }
        }
    )
}


@Composable
fun SearchTopbar() {
    var textState by remember { mutableStateOf("") }
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 8.dp),
        value = textState,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.surface.copy(alpha = .9f),
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
) {
    Box(modifier = modifier
        .fillMaxWidth()
        .height(200.dp)
        .clip(RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.BottomStart
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
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
            .background(brush = Brush.verticalGradient(
                colors = listOf(
                    Color.Transparent,
                    Color.Black
                ),
            ))
            .fillMaxWidth()
            .height(200.dp)
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
