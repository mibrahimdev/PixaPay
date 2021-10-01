package io.github.mohamedisoliman.pixapay.data.local

import io.github.mohamedisoliman.pixapay.data.entities.PixabayImage

interface LocalPixabayStoreContract {

    suspend fun getAllImage(): List<PixabayImage>

    suspend fun saveImages(images: List<PixabayImage>)

    suspend fun deleteAllImages()

}