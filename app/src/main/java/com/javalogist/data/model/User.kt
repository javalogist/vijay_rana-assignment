package com.javalogist.data.model

data class User(
    var userId: String?,
    var firstName: String?,
    var lastName: String?,
    var mobileNumber: String?,
    var emailAddress: String?,
    var password: String?
) {
    constructor() : this(null, "", "", "", "", "")
}
