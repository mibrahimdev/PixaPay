package io.github.mohamedisoliman.pixapay.data.entities


import com.squareup.moshi.Json

data class PixabaySearchResponse(
    @field:Json(name = "hits")
    val images: List<PixabayImage>? = listOf(),
    @field:Json(name = "total")
    val total: Int? = 0,
    @field:Json(name = "totalHits")
    val totalHits: Int? = 0,
)