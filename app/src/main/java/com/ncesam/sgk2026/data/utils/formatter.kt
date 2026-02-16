package com.ncesam.sgk2026.data.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


val POCKETBASE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd")


fun formatDate(date: LocalDate): String {
    val formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale("ru"))
    return date.format(formatter)
}