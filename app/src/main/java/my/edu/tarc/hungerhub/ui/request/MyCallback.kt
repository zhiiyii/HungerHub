package my.edu.tarc.hungerhub.ui.request

import my.edu.tarc.hungerhub.model.Request

interface MyCallback {
    fun onCallback(value: List<String>): Request
}