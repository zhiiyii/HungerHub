package my.edu.tarc.hungerhub.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import my.edu.tarc.hungerhub.repository.RequestRepository

class RequestViewModel: ViewModel() {
    // define private data
    private val _requestList = MutableLiveData<List<Request>>()
    // define global data - expose to other classes
    val requestList: LiveData<List<Request>> = _requestList

    private val requestRepository: RequestRepository = RequestRepository().getInstance()

    init {
        // Get a copy of request list from the repository
        requestRepository.loadRequest(_requestList)
    }
}