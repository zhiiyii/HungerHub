package my.edu.tarc.hungerhub.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import my.edu.tarc.hungerhub.model.Request

@Dao
interface RequestDao {
    // select all requests from all users
    @Query("SELECT * FROM request ORDER BY date DESC")
    fun getAllRequest(): LiveData<List<Request>>

    // select all requests within specified date/month/year for specific user
    @Query("SELECT * FROM request WHERE date LIKE :date || '%' ORDER BY date DESC")
    fun filterByDate(date: String): List<Request>

    // select all requests by status for specific users
    @Query("SELECT * FROM request WHERE lower(approvalStatus) = :status ORDER BY date DESC")
    fun filterByStatus(status: String): List<Request>

    // select all requests for a specific user
    @Query("SELECT * FROM request ORDER BY date DESC")
    fun removeFilter(): List<Request>

    @Query("DELETE FROM request")
    fun deleteAllRecords()

    @Insert
    suspend fun insert(request: Request)
}