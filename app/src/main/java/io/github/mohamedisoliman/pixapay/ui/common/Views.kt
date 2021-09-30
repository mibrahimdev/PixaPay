package io.github.mohamedisoliman.pixapay.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Comment
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.mohamedisoliman.pixapay.ui.search.IconChip
import io.github.mohamedisoliman.pixapay.ui.uiModels.ImageUiModel


@Composable
fun ImageChips(
    modifier: Modifier = Modifier,
    image: ImageUiModel,
    showExtraChips: Boolean = false,
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
            .padding(horizontal = 16.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .padding(bottom = 16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconChip(icon = Icons.Outlined.Person, image.userName)
                ExtraShips(showExtraChips, image)
            }
            TagsList(image.tags)
        }
    }
}

@Composable
private fun TagsList(tags: List<String>) {
    LazyRow(
        modifier = Modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        content = {
            itemsIndexed(tags) { _, item ->
                TagChip(tagName = item)
            }
        }
    )
}

@Composable
private fun ExtraShips(
    showExtraChips: Boolean,
    image: ImageUiModel,
) {
    if (showExtraChips) {
        IconChip(icon = Icons.Outlined.ThumbUp, image.likes)
        IconChip(icon = Icons.Outlined.Download, image.downloads)
        IconChip(icon = Icons.Outlined.Comment, image.comments)
    }
}

@Composable
fun TagChip(
    modifier: Modifier = Modifier,
    tagName: String,
) {
    Text(
        text = tagName,
        style = MaterialTheme.typography.caption.copy(color = Color.White)
    )
}
