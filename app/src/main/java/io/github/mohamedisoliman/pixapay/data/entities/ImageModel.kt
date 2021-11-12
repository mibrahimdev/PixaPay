package io.github.mohamedisoliman.pixapay.data.entities

data class ImageModel(
    val imageId: Long = -1,
    val userName: String = "",
    val url: String = "",
    val likes: String = "",
    val downloads: String = "",
    val comments: String = "",
    val tags: List<String> = emptyList(),
    val largeImageURL: String? = "",
)