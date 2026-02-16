package com.ncesam.sgk2026.domain.state

import android.net.Uri

data class ProfileState(
    val avatar: String? = null,
    val firstName: String = "",
    val email: String = "",
    val dateInfo: String = "",
    val gender: String = "",
    val notificationActive: Boolean = true
)

sealed interface ProfileEvent {
    object PolicyClicked: ProfileEvent
    object TermsOfServesClicked: ProfileEvent
    object LogoutClicked: ProfileEvent
    object NotificationToggle: ProfileEvent
    data class UploadAvatar(val uri: Uri): ProfileEvent

}

sealed interface ProfileEffect {
    object GoToLogin: ProfileEffect
    data class ShowSnackBar(val msg: String): ProfileEffect
}