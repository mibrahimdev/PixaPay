package io.github.mohamedisoliman.pixapay.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.mohamedisoliman.pixapay.data.ImagesRepository
import io.github.mohamedisoliman.pixapay.data.ImagesRepositoryContract
import io.github.mohamedisoliman.pixapay.data.remote.PixabayRemote
import io.github.mohamedisoliman.pixapay.data.remote.pixabayApi
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {


    @Provides
    @Singleton
    fun providesPixapayRemote(): PixabayRemote {
        return pixabayApi()
    }


    @Provides
    fun providesImageRepository(repository: ImagesRepository): ImagesRepositoryContract {
        return repository
    }

}