package io.github.mohamedisoliman.pixapay.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.mohamedisoliman.pixapay.data.ImagesRepository
import io.github.mohamedisoliman.pixapay.data.ImagesRepositoryContract
import io.github.mohamedisoliman.pixapay.data.local.*
import io.github.mohamedisoliman.pixapay.data.remote.RemotePixabayContract
import io.github.mohamedisoliman.pixapay.data.remote.pixabayApi
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {


    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): AppDatabase {
        return makeDatabase(context)
    }

    @Provides
    @Singleton
    fun providesImagesDao(appDatabase: AppDatabase): ImageDao {
        return appDatabase.imagesDao()
    }


    @Provides
    @Singleton
    fun providesPixabayLocal(dao: ImageDao): LocalPixabayStoreContract {
        return LocalPixabayStore(dao)
    }


    @Provides
    @Singleton
    fun providesPixapayRemote(): RemotePixabayContract {
        return pixabayApi()
    }


    @Provides
    fun providesImageRepository(repository: ImagesRepository): ImagesRepositoryContract {
        return repository
    }

}