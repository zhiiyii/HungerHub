package my.edu.tarc.hungerhub.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import my.edu.tarc.hungerhub.model.Request

class RequestRepository {
    private val reference: DatabaseReference = FirebaseDatabase.getInstance().getReference("Requests")

    @Volatile private var INSTANCE: RequestRepository ?= null

    fun getInstance(): RequestRepository {
        return INSTANCE ?: synchronized(this) {
            val instance = RequestRepository()
            INSTANCE = instance
            instance
        }
    }

    fun loadRequest(requestList: MutableLiveData<List<Request>>) {
        reference.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val _requestList: List<Request> = snapshot.children.map { dataSnapshot ->
                        dataSnapshot.getValue(Request::class.java)!!
                    }
                    Log.d("request items", _requestList.toString())
                } catch (_: Exception) {
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}