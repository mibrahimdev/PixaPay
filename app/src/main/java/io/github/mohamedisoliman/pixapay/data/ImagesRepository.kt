package io.github.mohamedisoliman.pixapay.data

import io.github.mohamedisoliman.pixapay.data.entities.PixabayImage
import io.github.mohamedisoliman.pixapay.data.local.LocalPixabayStoreContract
import io.github.mohamedisoliman.pixapay.data.remote.RemotePixabayContract
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ImagesRepository @Inject constructor(
    private val remote: RemotePixabayContract,
    private val local: LocalPixabayStoreContract,
) : ImagesRepositoryContract {

    override fun search(query: String): Flow<List<PixabayImage>> =
        flow {
            val images = remote.search(query).images
            if (images != null && images.isNotEmpty()) {
                local.deleteAllImages()
                local.saveImages(images)
            }
            emit(local.getAllImage())
        }
}