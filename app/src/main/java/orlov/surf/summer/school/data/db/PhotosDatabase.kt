package orlov.surf.summer.school.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import orlov.surf.summer.school.data.db.entity.PhotoEntity


@Database(entities = [PhotoEntity::class], exportSchema = false, version = 1)
abstract class PhotosDatabase : RoomDatabase() {

    abstract fun getPhotosDao(): PhotosDao

    companion object {

        private const val DATABASE_NAME = "photos.db"

        @Volatile
        private var instance: PhotosDatabase? = null

        fun getInstance(context: Context): PhotosDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }
        }

        private fun buildDatabase(context: Context): PhotosDatabase {
            val database =
                Room.databaseBuilder(context, PhotosDatabase::class.java, DATABASE_NAME)
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                        }
                    })
                    .build()

            return database
        }
    }


}