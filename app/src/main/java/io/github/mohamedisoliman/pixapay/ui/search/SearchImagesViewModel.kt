package io.github.mohamedisoliman.pixapay.ui.search

import androidx.lifecycle.ViewModel

class SearchImagesViewModel : ViewModel() {

    val images = (0..20).map { previewImage }
        .mapIndexed { index, imageUiModel ->
            imageUiModel.copy(userName = "User$index",
                imageId = index + 100L)
        }


    fun findImage(id: Long): ImageUiModel? {
        return images.find { it.imageId == id }
    }

}
