package my.edu.tarc.hungerhub.model

data class RequestFirebase(
    var date: String,
    var income: Int,
    var jobStatus: String,
    var maritalStatus: String,
    var pax: Int,
    var reason: String,
    var approvalStatus: String
)