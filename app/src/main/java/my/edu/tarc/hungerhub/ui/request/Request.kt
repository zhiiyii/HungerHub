package my.edu.tarc.hungerhub.ui.request

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Request (
    val maritalStatus: String,
    val jobStatus: String,
    val income: Int,
    val reason: String,
    val pax: Int,
    @PrimaryKey val date: String
    )