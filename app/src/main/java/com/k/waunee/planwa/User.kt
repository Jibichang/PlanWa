package com.k.waunee.planwa

class User(var user :String? = "", var email :String? = "") {

    companion object {
        var USER_KEY = "user"
        var EMAIL_KEY = "email"
    }
}