package com.tech.momenti.data.model




/**************************  sign api response *********************/

data class SignupApiResponse(
    var `data`: Data?,
    var message: String?,
    var success: Boolean?
) {
    data class Data(
        var user: User?
    ) {
        data class User(
            var _id: String?,
            var email: String?,
            var isSocialLogin: Boolean?,
            var profileImage: Any?
        )
    }
}

/**************************  verify otp  api response *********************/


data class VerifyOtpApiResponse(
    var message: String?,
    var success: Boolean?,
    var token: String?,
    var type: Int?,
    var user: User?
) {
    data class User(
        var _id: String?,
        var email: String?,
        var isSocialLogin: Boolean?,
        var profileImage: Any?
    )
}