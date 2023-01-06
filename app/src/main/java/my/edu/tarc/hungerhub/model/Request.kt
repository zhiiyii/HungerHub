package my.edu.tarc.hungerhub.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import my.edu.tarc.hungerhub.adapter.RequestAdapter

private val requestAdapter = RequestAdapter()

@Entity
data class Request (
    @PrimaryKey var date: String,
    var income: Int,
    var jobStatus: String,
    var maritalStatus: String,
    var pax: Int,
    var reason: String,
    var approvalStatus: String
    ) {
    constructor(): this(((0..100).random()).toString(),
        0, "", "", 0, "", "")
    // random number to minimize the probability of data from firebase to load too slow
    // although already use thread
}