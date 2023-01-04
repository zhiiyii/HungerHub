package my.edu.tarc.hungerhub.ui.home

import android.widget.RadioGroup

data class User(
    val ic: String? = null,
    val email: String? = null,
    val name: String? = null,
    val state: String? = null,
    val pass: String? = null,
    val phoneNo: String? = null,
    val address: String,
    val posCode: String,
    val type: String? = null
) {
    constructor() : this(null, null, null, null, null, null, "", "",null)

    override fun toString(): String {
        return "User(ic=$ic, email=$email, name=$name, state=$state, pass=$pass, phoneNo=$phoneNo, address='$address', posCode='$posCode', type = '$type')"
    }

}

//class User(val ic: String? = null, val email: String? = null, val name: String? = null,
//val state: String? = null, val pass: String? = null, val phoneNo: String? = null,
//val address: String, val posCode: String,){
//    constructor()
//}
