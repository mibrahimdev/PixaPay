package io.github.mohamedisoliman.pixapay.data.entities


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity
data class PixabayImage(
    @PrimaryKey
    @field:Json(name = "id")
    val id: Int? = 0,
    @field:Json(name = "collections")
    val collections: Int? = 0,
    @field:Json(name = "comments")
    val comments: Int? = 0,
    @field:Json(name = "downloads")
    val downloads: Int? = 0,
    @field:Json(name = "imageHeight")
    val imageHeight: Int? = 0,
    @field:Json(name = "imageSize")
    val imageSize: Int? = 0,
    @field:Json(name = "imageWidth")
    val imageWidth: Int? = 0,
    @field:Json(name = "largeImageURL")
    val largeImageURL: String? = "",
    @field:Json(name = "likes")
    val likes: Int? = 0,
    @field:Json(name = "pageURL")
    val pageURL: String? = "",
    @field:Json(name = "previewHeight")
    val previewHeight: Int? = 0,
    @field:Json(name = "previewURL")
    val previewURL: String? = "",
    @field:Json(name = "previewWidth")
    val previewWidth: Int? = 0,
    @field:Json(name = "tags")
    val tags: String? = "",
    @field:Json(name = "type")
    val type: String? = "",
    @field:Json(name = "user")
    val user: String? = "",
    @field:Json(name = "user_id")
    val userId: Int? = 0,
    @field:Json(name = "userImageURL")
    val userImageURL: String? = "",
    @field:Json(name = "views")
    val views: Int? = 0,
    @field:Json(name = "webformatHeight")
    val webformatHeight: Int? = 0,
    @field:Json(name = "webformatURL")
    val webformatURL: String? = "",
    @field:Json(name = "webformatWidth")
    val webformatWidth: Int? = 0,
)


fun PixabayImage.toImageUiModel() = ImageModel(
    imageId = id?.toLong() ?: -1,
    userName = user ?: "",
    url = previewURL ?: "",
    likes = likes.toString(),
    downloads = downloads.toString(),
    comments = comments.toString(),
    tags = tags?.split(", ") ?: emptyList(),
    largeImageURL = largeImageURL,
)