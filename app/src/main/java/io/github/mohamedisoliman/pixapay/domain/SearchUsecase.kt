package io.github.mohamedisoliman.pixapay.domain

import io.github.mohamedisoliman.pixapay.data.ImagesRepositoryContract
import io.github.mohamedisoliman.pixapay.data.entities.toImageUiModel
import io.github.mohamedisoliman.pixapay.ui.uiModels.ImageUiModel
import kotlinx.coroutines.flow.*

class SearchUsecase(
    private val imagesRepository: ImagesRepositoryContract,
) : (String) -> Flow<List<ImageUiModel>> {

    override fun invoke(query: String): Flow<List<ImageUiModel>> {
        return imagesRepository.search(query)
            .map { list -> list.map { it.toImageUiModel() } }
    }

}