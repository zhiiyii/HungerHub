package my.edu.tarc.hungerhub.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import my.edu.tarc.hungerhub.database.RequestDao
import my.edu.tarc.hungerhub.model.Request

class RequestRepository(private val requestDao: RequestDao) {
    // create a cache copy of data in the DAO
    val allRequest: LiveData<List<Request>> = requestDao.getAllRequest()

    @WorkerThread // launch suspend function, only in coroutine
    suspend fun insert(request: Request) {
        requestDao.insert(request)
    }
}