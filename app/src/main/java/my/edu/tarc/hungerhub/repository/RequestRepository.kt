package my.edu.tarc.hungerhub.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import my.edu.tarc.hungerhub.dao.RequestDao
import my.edu.tarc.hungerhub.model.Request

class RequestRepository(private val requestDao: RequestDao) {
    // create a cache copy of data in the DAO
    val allRequest: LiveData<List<Request>> = requestDao.getAllRequest()

    @WorkerThread // launch suspend function, only in coroutine
    suspend fun insert(request: Request) {
        requestDao.insert(request)
    }

    fun filterByDate(date: String): List<Request> {
        return requestDao.filterByDate(date)
    }

    fun filterByStatus(status: String): List<Request> {
        return requestDao.filterByStatus(status)
    }

    fun removeFilter(): List<Request> {
        return requestDao.removeFilter()
    }

    fun deleteAllRecords() {
        return requestDao.deleteAllRecords()
    }
}