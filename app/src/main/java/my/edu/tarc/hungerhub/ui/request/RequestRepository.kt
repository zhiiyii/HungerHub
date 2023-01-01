package my.edu.tarc.hungerhub.ui.request

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class RequestRepository(private val requestDao: RequestDao) {
    // Create a cache copy of data in the DAO
    val allRequest: LiveData<List<Request>> = requestDao.getAllRequest()

    @WorkerThread
    suspend fun insert(request: Request) { // launch suspend function, only in coroutine
        requestDao.insert(request)
    }

    @WorkerThread
    suspend fun update(request: Request) {
        requestDao.update(request)
    }

//    fun findByDate(date: String): List<Request> {
//        return requestDao.findByDate(date)
//    }
}