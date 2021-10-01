package io.github.mohamedisoliman.pixapay.data.local

import android.content.Context
import androidx.room.*
import io.github.mohamedisoliman.pixapay.data.entities.PixabayImage


@Database(entities = [PixabayImage::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun imagesDao(): ImageDao
}


@Dao
interface ImageDao {

    @Query("SELECT * FROM PixabayImage")
    suspend fun getAllImages(): List<PixabayImage>

    @Insert
    suspend fun insertAll(images: List<PixabayImage>)

    @Query("DELETE FROM PixabayImage")
    suspend fun deleteAll()

}

fun makeDatabase(context: Context) = Room.databaseBuilder(
    context,
    AppDatabase::class.java, "pixapay-database-name"
).build()