package my.edu.tarc.hungerhub.model

import androidx.room.Entity
import androidx.room.PrimaryKey

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
    constructor(): this("", 0, "", "", 0, "", "")
}