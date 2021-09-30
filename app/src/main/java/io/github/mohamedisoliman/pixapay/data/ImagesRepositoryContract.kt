package io.github.mohamedisoliman.pixapay.data

import io.github.mohamedisoliman.pixapay.data.entities.Hit
import kotlinx.coroutines.flow.Flow

interface ImagesRepositoryContract {

    fun search(query: String): Flow<List<Hit>>
}