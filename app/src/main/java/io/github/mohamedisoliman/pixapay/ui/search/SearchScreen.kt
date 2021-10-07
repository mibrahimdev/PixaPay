package io.github.mohamedisoliman.pixapay.ui.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Pets
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import io.github.mohamedisoliman.pixapay.R
import io.github.mohamedisoliman.pixapay.UiState
import io.github.mohamedisoliman.pixapay.data.entities.ImageModel
import io.github.mohamedisoliman.pixapay.domain.SearchState
import io.github.mohamedisoliman.pixapay.ui.common.ImageChips
import io.github.mohamedisoliman.pixapay.ui.common.isPortrait
import io.github.mohamedisoliman.pixapay.ui.common.toUiModel
import io.github.mohamedisoliman.pixapay.ui.search.SearchScreenEvent.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview


@Preview
@Composable
fun PreviewSearch() {
    SearchScreenContent()
}

@OptIn(FlowPreview::class)
@ExperimentalCoroutinesApi
@Composable
fun SearchScreen(viewModel: SearchImagesViewModel) {
    val viewState by viewModel.states.collectAsState()
    val query by viewModel.query.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var imageId by remember { mutableStateOf(-1L) }


    SearchScreenContent(
        searchText = query,
        searchMainView = {
            viewState.StateToMainView(showDialog = {
                showDialog = it
            }, imageId = {
                imageId = it
            })

        },
        onSearchChange = { viewModel.onSearchQueryChange(it) },
        onSearchClicked = { viewModel.emitEvent(SearchClicked(query)) },
        isLoading = viewState.isLoading
    )

    ConfirmationDialog(showDialog = showDialog, onConfirm = {
        showDialog = false
        viewModel.navigateToDetails(imageId)
    }) {
        showDialog = false
    }
}

@Composable
private fun UiState.StateToMainView(
    imageId: (Long) -> Unit,
    showDialog: (Boolean) -> Unit,
) {
    when (this) {
        is SearchState.EmptyResult -> EmptyView()
        is SearchState.Error -> this.throwable?.let { ErrorView(it) }
        is SearchState.Success -> this.result?.let { list ->
            ImageListView(list) {
                imageId(it)
                showDialog(true)
            }
        }
        else -> { }
    }
}

@Composable
fun ConfirmationDialog(
    showDialog: Boolean = false,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    if (showDialog)
        AlertDialog(
            modifier = Modifier.fillMaxWidth(),
            title = { Text(stringResource(R.string.dialog_title_open_image_details)) },
            onDismissRequest = onDismiss,
            buttons = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.End,

                    ) {
                    TextButton(onClick = onDismiss) {
                        Text(text = stringResource(R.string.no))
                    }
                    TextButton(onClick = onConfirm) {
                        Text(text = stringResource(R.string.yes))
                    }

                }
            }
        )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun SearchScreenContent(
    searchText: String = "",
    isLoading: Boolean = false,
    onSearchChange: (String) -> Unit = {},
    onSearchClicked: () -> Unit = {},
    searchMainView: @Composable () -> Unit = {},
) {
    Box(
        modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp),
        contentAlignment = Alignment.TopCenter
    ) {

        searchMainView()

        SearchTopbar(
            searchText = searchText,
            onSearchChange = onSearchChange,
            onSearchClicked = onSearchClicked,
            isLoading = isLoading,
        )

    }

}

@Composable
fun EmptyView(modifier: Modifier = Modifier) {
    PlaceHolderView(
        modifier = modifier,
        icon = Icons.Outlined.Pets,
        stringResource(R.string.empty_view_message)
    )
}

@Composable
fun PlaceHolderView(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    message: String,
    iconTint: Color = MaterialTheme.colors.secondaryVariant,
    textColor: Color = Color.Unspecified,
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.Center),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier.size(120.dp),
                imageVector = icon,
                tint = iconTint.copy(alpha = 0.5f),
                contentDescription = ""
            )
            Text(
                text = message,
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Center,
                color = textColor.copy(alpha = 0.5f)
            )
        }
    }
}

@Composable
fun ErrorView(
    throwable: Throwable,
) {
    val uiModel = throwable.toUiModel()
    PlaceHolderView(
        icon = uiModel.errorIconId,
        message = stringResource(id = uiModel.errorMessageId),
        iconTint = MaterialTheme.colors.error
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageListView(
    images: List<ImageModel>,
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
                    onImageClicked = { onImageClicked(it) }
                )
            }
        }
    )
}


@Composable
fun SearchTopbar(
    modifier: Modifier = Modifier,
    searchText: String = "",
    onSearchChange: (String) -> Unit = {},
    onSearchClicked: () -> Unit = {},
    isLoading: Boolean,
) {
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 8.dp),
        value = searchText,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.surface,
            cursorColor = MaterialTheme.colors.onSurface,
            disabledLabelColor = MaterialTheme.colors.surface,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        onValueChange = {
            onSearchChange(it)
        },
        shape = RoundedCornerShape(8.dp),
        singleLine = true,
        trailingIcon = { TrailingIcon(isLoading, onSearchClicked) },
        keyboardActions = KeyboardActions(onDone = { onSearchClicked() })
    )
}

@Composable
private fun TrailingIcon(isLoading: Boolean, onSearchClicked: () -> Unit) {
    if (isLoading) {
        CircularProgressIndicator(
            modifier = Modifier.size(20.dp),
            strokeWidth = 2.dp
        )
    } else {
        IconButton(onClick = { onSearchClicked() }) {
            Icon(
                imageVector = Icons.Outlined.Search,
                tint = MaterialTheme.colors.onSurface,
                contentDescription = ""
            )
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ImageCard(
    modifier: Modifier = Modifier,
    image: ImageModel,
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