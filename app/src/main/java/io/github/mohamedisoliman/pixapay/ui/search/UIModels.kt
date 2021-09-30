package io.github.mohamedisoliman.pixapay.ui.search

import io.github.mohamedisoliman.pixapay.ui.models.ImageUiModel


object PreviewData {
    val image = ImageUiModel(
        userName = "Ibrahim",
        url = "https://images.unsplash.com/photo-1469854523086-cc02fe5d8800?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1308&q=80",
        likes = 23.toString(),
        downloads = 330.toString(),
        comments = 16.toString(),
        tags = listOf(
            "nature",
            "landscape",
            "outing",
            "nature",
            "landscape",
            "outing",
            "nature",
            "landscape",
            "outing"
        ).map { "#$it" },
        largeImageURL = "https://images.unsplash.com/photo-1469854523086-cc02fe5d8800?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1308&q=80",
    )

    val images = (0..20).map { image }
        .mapIndexed { index, imageUiModel ->
            imageUiModel.copy(userName = "User$index",
                imageId = index + 100L)
        }
}
