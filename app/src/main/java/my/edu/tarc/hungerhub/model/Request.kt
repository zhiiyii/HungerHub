package my.edu.tarc.hungerhub.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Request (
    @PrimaryKey var date: String,
    var name: String,
    var ic: String,
    var phoneNo: String,
    var loginEmail: String,
    var address: String,
    var postcode: String,
    var state: String,
    var income: Int,
    var jobStatus: String,
    var maritalStatus: String,
    var pax: Int,
    var reason: String,
    var approvalStatus: String
    )