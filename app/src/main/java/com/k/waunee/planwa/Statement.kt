package com.k.waunee.planwa

import com.google.firebase.database.Exclude

data class Statement(
    var Name :String? = "",
    var Date :String? = "",
    var Price :String? = "",
    var Type :String? = ""
) {
    companion object {
        var STATEMENT_KEY = "statement"
    }

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "name" to Name,
            "date" to Date,
            "price" to Price,
            "type" to Type
        )
    }
}