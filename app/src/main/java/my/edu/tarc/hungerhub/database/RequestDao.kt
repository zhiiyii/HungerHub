package my.edu.tarc.hungerhub.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import my.edu.tarc.hungerhub.model.Request

@Dao
interface RequestDao {
    @Query("SELECT * FROM request ORDER BY date DESC")
    fun getAllRequest(): LiveData<List<Request>>

    @Query("SELECT * FROM request WHERE date LIKE :date || '%' ORDER BY date DESC")
    fun filterByDate(date: String): List<Request>

    @Query("SELECT * FROM request ORDER BY date DESC")
    fun removeFilter(): List<Request>

    @Insert
    suspend fun insert(request: Request)
}