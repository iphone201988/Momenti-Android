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

/**************************  forgot password   api response *********************/

data class ForgotPasswordApiResponse(
    var message: String?,
    var success: Boolean?,
    var user: User?
) {
    data class User(
        var _id: String?,
        var email: String?,
        var isSocialLogin: Boolean?,
        var profileImage: Any?
    )
}

/**************************  change  password   api response *********************/
data class ChangePasswordApiResponse(
    var message: String?,
    var success: Boolean?,
    var user: User?
) {
    data class User(
        var _id: String?,
        var email: String?,
        var isSocialLogin: Boolean?,
        var profileImage: Any?
    )
}


/**************************  add gratitude   api response *********************/


data class AddGratitudeApiResponse(
    var `data`: Data?,
    var message: String?,
    var success: Boolean?
) {
    data class Data(
        var __v: Int?,
        var _id: String?,
        var createdAt: String?,
        var date: String?,
        var gratitudes: List<Gratitude?>?,
        var updatedAt: String?,
        var userId: String?
    ) {
        data class Gratitude(
            var _id: String?,
            var text: String?
        )
    }
}

/**************************  life area    api response *********************/
data class LifeAreaResponse(
    var `data`: List<Data?>?,
    var message: String?,
    var success: Boolean?
) {
    data class Data(
        var __v: Int?,
        var _id: String?,
        var createdAt: String?,
        var lifeArea: String?,
        var updatedAt: String?
    )
}


data class AddTaskApiResponse(
    var `data`: Data?,
    var message: String?,
    var success: Boolean?
) {
    data class Data(
        var __v: Int?,
        var _id: String?,
        var createdAt: String?,
        var date: String?,
        var tasks: List<Task?>?,
        var updatedAt: String?,
        var userId: String?
    ) {
        data class Task(
            var _id: String?,
            var lifeAreaId: String?,
            var status: String?,
            var title: String?
        )
    }
}