package my.edu.tarc.hungerhub.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import my.edu.tarc.hungerhub.dao.RequestDao
import my.edu.tarc.hungerhub.model.Request

@Database(entities = [Request::class], version = 2, exportSchema = false)
abstract class RequestDatabase: RoomDatabase() {
    abstract fun requestDao(): RequestDao

    companion object {
        @Volatile // data can be destroyed by uninstalling app
        private var INSTANCE: RequestDatabase? = null

        fun getDatabase(context: Context): RequestDatabase {
            val tempInstance = INSTANCE

            if (tempInstance != null) { // database exist
                return tempInstance
            }

            // Create an instance of the database
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RequestDatabase::class.java,
                    "requests"
                ).allowMainThreadQueries().build()

                INSTANCE = instance
                return instance
            }
        }
    }
}