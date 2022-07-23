package orlov.surf.summer.school.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import orlov.surf.summer.school.data.db.entity.PhotoEntity


@Dao
interface PhotosDao {

    @Query("SELECT * FROM photos")
    fun getAllPhotos(): LiveData<List<PhotoEntity>>

    @Query("SELECT * FROM photos WHERE title LIKE '%' || :searchQuery || '%'")
    fun searchPhotos(searchQuery: String): LiveData<List<PhotoEntity>>

    @Query("SELECT * FROM photos WHERE isLiked = 1 ORDER BY dateLiked DESC")
    fun getSavedPhotos(): LiveData<List<PhotoEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPhotos(articlesList: List<PhotoEntity>)

    @Query("DELETE FROM photos")
    suspend fun deletePhotos()

    @Query("UPDATE photos SET isLiked = :newIsLiked, dateLiked = :dateLiked WHERE id = :id")
    fun likePhoto(newIsLiked: Boolean, id: String, dateLiked: Long = System.currentTimeMillis())

    @Transaction
    suspend fun updatePhotos(
        articlesList: List<PhotoEntity>
    ) {
        deletePhotos()
        insertPhotos(articlesList)
    }

}