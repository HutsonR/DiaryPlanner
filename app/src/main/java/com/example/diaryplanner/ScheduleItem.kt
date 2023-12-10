package com.example.diaryplanner

data class ScheduleItem(
    val startTime: String,
    val endTime: String?,
    val color: Int,
    val icon: Int,
    val text: String,
    val duration: String,
    var isCompleteTask: Boolean = false
)