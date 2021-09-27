package io.github.mohamedisoliman.pixapay.ui.search

import androidx.lifecycle.ViewModel

class SearchImagesViewModel : ViewModel() {

    val images = PreviewData.images


    fun findImage(id: Long): ImageUiModel? {
        return images.find { it.imageId == id }
    }

}
