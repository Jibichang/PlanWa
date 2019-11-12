package com.k.waunee.planwa

data class Statement(
    var Name :String? = "",
    var Date :String? = "",
    var Price :String? = "",
    var Type :String? = ""
) {
    companion object {
        var STATEMENT_KEY = "statement"
    }
}