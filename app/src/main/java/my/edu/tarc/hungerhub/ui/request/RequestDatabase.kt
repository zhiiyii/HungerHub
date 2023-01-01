package my.edu.tarc.hungerhub.ui.request

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Request::class], version = 1, exportSchema = false)
abstract class RequestDatabase: RoomDatabase() {
    abstract fun requestDao(): RequestDao

    companion object {
        @Volatile // can be destroyed, uninstall app, data destroyed
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
                    "request_db"
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}