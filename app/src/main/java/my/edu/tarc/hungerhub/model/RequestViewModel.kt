package my.edu.tarc.hungerhub.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import my.edu.tarc.hungerhub.repository.RequestRepository
import my.edu.tarc.hungerhub.database.RequestDatabase

class RequestViewModel(application: Application): AndroidViewModel(application) {
    private val _requestList = MutableLiveData<List<Request>>()
    var requestList: LiveData<List<Request>> = _requestList

    private val requestRepository: RequestRepository

    init {
        // initialize DAO
        val requestDao = RequestDatabase.getDatabase(application).requestDao()
        // associate DAO to Repository
        requestRepository = RequestRepository(requestDao)
        // get a copy of request list from the repository
        requestList = requestRepository.allRequest
    }

    fun insert(request: Request) = viewModelScope.launch {
        requestRepository.insert(request)
    }

    fun filterByDate(date: String): List<Request> {
        return requestRepository.filterByDate(date)
    }

    fun filterByStatus(status: String): List<Request> {
        return requestRepository.filterByStatus(status)
    }

    fun removeFilter(): List<Request> {
        return requestRepository.removeFilter()
    }

    fun deleteAllRecords() {
        return requestRepository.deleteAllRecords()
    }
}