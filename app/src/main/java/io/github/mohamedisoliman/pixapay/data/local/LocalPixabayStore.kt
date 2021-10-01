package io.github.mohamedisoliman.pixapay.data.local

import io.github.mohamedisoliman.pixapay.data.entities.PixabayImage
import javax.inject.Inject

class LocalPixabayStore @Inject constructor(
    private val imageDao: ImageDao,
) : LocalPixabayStoreContract {


    override suspend fun getAllImage(): List<PixabayImage> {
        return imageDao.getAllImages()
    }

    override suspend fun saveImages(images: List<PixabayImage>) {
        return imageDao.insertAll(images)
    }

    override suspend fun deleteAllImages() {
        imageDao.deleteAll()
    }
}