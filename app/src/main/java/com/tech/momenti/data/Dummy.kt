package com.tech.momenti.data


data class DayWithWeekday(
    val day: String,
    val weekday: String,
    val isCurrentDay:Boolean=false,
    val currentYear:Int,
    val currentMonnth:Int
)

data class TaskData(
    var count : Int,
    var des : String
)

data class ProfileData(
    var icon : Int,
    var title : String,
    var type : Int,
    var textColor : Int
)


data class TaskHistory(
    var date : String,
    var tasks : List<TaskData>,
    var isExpanded: Boolean = false
)

data class CategoryProgress(
    val name: String,
    val progress: Int
)


data class Notification(
    var date : String,
    var list : List<NotificationData>

)

data class NotificationData(
    var title: String,
    var notification : String,
    var time : String
)

data class Gratitude(
    var gratitude : String,
    var date: String
)

data class FocusArea(
    var focus : String
)

data class Onboarding(
    var title : String
)