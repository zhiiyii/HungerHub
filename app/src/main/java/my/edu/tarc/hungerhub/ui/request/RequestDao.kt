package my.edu.tarc.hungerhub.ui.request

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface RequestDao {
    // DAO = Data Access Object

    // Query (R - retrieve)
    @Query("SELECT * FROM request")
    fun getAllRequest():LiveData<List<Request>>

//    @Query("SELECT * FROM request WHERE date LIKE :date")
//    fun findByDate(name: String): List<Request>

    // Data Manipulation function (CUD - create, update, delete)
    @Insert
    suspend fun insert(request: Request)

    @Update
    suspend fun update(request: Request)
}