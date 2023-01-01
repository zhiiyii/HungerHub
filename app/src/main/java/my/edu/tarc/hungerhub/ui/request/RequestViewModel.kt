package my.edu.tarc.hungerhub.ui.request

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class RequestViewModel(application: Application): AndroidViewModel(application) {
    // define private data
    private val _requestList = MutableLiveData<List<Request>>()
    // define global data - expose to other classes
    var requestList: LiveData<List<Request>> = _requestList

    private val requestRepository: RequestRepository

    init {
        // Initialize DAO
        val requestDao = RequestDatabase.getDatabase(application).requestDao()
        // Associate DAO to Repository
        requestRepository = RequestRepository(requestDao)
        // Get a copy of request list from the repository
        requestList = requestRepository.allRequest
    }

    // global scope can run from activity, view model scope is for coroutine
    fun insert(request: Request) = viewModelScope.launch {
        requestRepository.insert(request)
    }

    fun update(request: Request) = viewModelScope.launch {
        requestRepository.update(request)
    }

//    fun findByDate(date: String): List<Request> {
//        return requestRepository.findByDate(date)
//    }
}