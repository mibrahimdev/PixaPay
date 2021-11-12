package io.github.mohamedisoliman.pixapay

import io.github.mohamedisoliman.pixapay.data.entities.ImageModel


@DslMarker
annotation class ImageDSL


fun image(block: ImageBuilder.() -> Unit): ImageModel = ImageBuilder().apply(block).build()

@ImageDSL
class ImageBuilder {
    var imageId: Long = -1
    var userName: String = ""
    var url: String = ""
    var likes: String = ""
    var downloads: String = ""
    var comments: String = ""
    private var tags: MutableList<String> = mutableListOf()
    var largeImageURL: String? = ""

    fun tag(block: ImageBuilder.() -> String) {
        tags.add(block())
    }

    fun build(): ImageModel = ImageModel(
        imageId = imageId,
        userName = userName,
        url = url,
        likes = likes,
        downloads = downloads,
        comments = comments,
        tags = tags,
        largeImageURL = largeImageURL
    )
}

fun main() {
    val image = image {
        comments = "20"
        downloads = "30"
        tag { "Nature" }
        tag { "People" }
        tag {
            comments = "40"
            "Travel"

        }
    }

    print(image)
}