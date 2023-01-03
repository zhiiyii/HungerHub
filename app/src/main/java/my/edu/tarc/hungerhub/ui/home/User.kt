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
    val RorD: RadioGroup
    )

