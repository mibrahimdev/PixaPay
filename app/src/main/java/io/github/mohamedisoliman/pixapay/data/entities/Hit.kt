package io.github.mohamedisoliman.pixapay.data.entities


import com.squareup.moshi.Json
import io.github.mohamedisoliman.pixapay.ui.uiModels.ImageUiModel

data class Hit(
    @Json(name = "collections")
    val collections: Int? = 0,
    @Json(name = "comments")
    val comments: Int? = 0,
    @Json(name = "downloads")
    val downloads: Int? = 0,
    @Json(name = "id")
    val id: Int? = 0,
    @Json(name = "imageHeight")
    val imageHeight: Int? = 0,
    @Json(name = "imageSize")
    val imageSize: Int? = 0,
    @Json(name = "imageWidth")
    val imageWidth: Int? = 0,
    @Json(name = "largeImageURL")
    val largeImageURL: String? = "",
    @Json(name = "likes")
    val likes: Int? = 0,
    @Json(name = "pageURL")
    val pageURL: String? = "",
    @Json(name = "previewHeight")
    val previewHeight: Int? = 0,
    @Json(name = "previewURL")
    val previewURL: String? = "",
    @Json(name = "previewWidth")
    val previewWidth: Int? = 0,
    @Json(name = "tags")
    val tags: String? = "",
    @Json(name = "type")
    val type: String? = "",
    @Json(name = "user")
    val user: String? = "",
    @Json(name = "user_id")
    val userId: Int? = 0,
    @Json(name = "userImageURL")
    val userImageURL: String? = "",
    @Json(name = "views")
    val views: Int? = 0,
    @Json(name = "webformatHeight")
    val webformatHeight: Int? = 0,
    @Json(name = "webformatURL")
    val webformatURL: String? = "",
    @Json(name = "webformatWidth")
    val webformatWidth: Int? = 0,
)


fun Hit.toImageUiModel() = ImageUiModel(
    imageId = id?.toLong() ?: -1,
    userName = user ?: "",
    url = previewURL ?: "",
    likes = likes.toString(),
    downloads = downloads.toString(),
    comments = comments.toString(),
    tags = tags?.split(", ") ?: emptyList(),
    largeImageURL = largeImageURL,
)