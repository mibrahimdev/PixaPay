package io.github.mohamedisoliman.pixapay.data

import io.github.mohamedisoliman.pixapay.data.entities.Hit
import io.github.mohamedisoliman.pixapay.data.remote.PixabayRemote
import kotlinx.coroutines.flow.*

class ImagesRepository(private val remote: PixabayRemote) : ImagesRepositoryContract {

    override fun search(query: String): Flow<List<Hit>> = flow {
        val images = remote.search(query).hits
        emit(images!!)
    }
}