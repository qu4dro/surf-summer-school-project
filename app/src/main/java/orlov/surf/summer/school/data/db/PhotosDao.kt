package orlov.surf.summer.school.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import orlov.surf.summer.school.data.db.entity.PhotoEntity


@Dao
interface PhotosDao {

    @Query("SELECT * FROM photos")
    fun getAllPhotos(): LiveData<List<PhotoEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPhotos(articlesList: List<PhotoEntity>)

    @Query("DELETE FROM photos")
    suspend fun deletePhotos()

//    @Query("UPDATE photos SET isLiked = :newIsLiked WHERE id = :id")
//    fun updatePhoto(newIsLiked: Boolean, id: String)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updatePhoto(photo: PhotoEntity)

    @Transaction
    suspend fun updatePhotos(
        articlesList: List<PhotoEntity>
    ) {
        deletePhotos()
        insertPhotos(articlesList)
    }

}