package io.github.mohamedisoliman.pixapay.data.entities


import com.squareup.moshi.Json

data class PixabaySearchDto(
    @Json(name = "hits")
    val hits: List<Hit>? = listOf(),
    @Json(name = "total")
    val total: Int? = 0,
    @Json(name = "totalHits")
    val totalHits: Int? = 0
)