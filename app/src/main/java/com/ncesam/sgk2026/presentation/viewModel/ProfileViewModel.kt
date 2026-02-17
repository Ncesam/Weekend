package com.ncesam.sgk2026.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ncesam.sgk2026.data.utils.formatDate
import com.ncesam.sgk2026.data.utils.validateDate
import com.ncesam.sgk2026.domain.repository.UserRepository
import com.ncesam.sgk2026.domain.state.ProfileEffect
import com.ncesam.sgk2026.domain.state.ProfileEvent
import com.ncesam.sgk2026.domain.state.ProfileState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class ProfileViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()


    init {
        viewModelScope.launch {
            userRepository.getUser()
                .onSuccess { user ->
                    Log.d("Test", user.avatar)
                    _state.update { state ->
                        state.copy(
                            firstName = user.firstName,
                            email = user.email,
                            dateInfo = formatDate(
                                validateDate(user.born) ?: return@onSuccess
                            ),
                            gender = user.gender.slice((0..3)),
                            notificationActive = userRepository.getNotificationActive()
                        )
                    }
                }
                .onFailure { _effect.send(ProfileEffect.ShowSnackBar("Проверьте подключение к интернету")) }
        }
    }


    private val _effect = Channel<ProfileEffect>()
    val effect = _effect.receiveAsFlow()

    suspend fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.UploadAvatar -> {
                userRepository.uploadAvatar(event.uri).onFailure {
                    _effect.send(ProfileEffect.ShowSnackBar("Не удалось загрузить аватара"))
                }.onSuccess { user ->
                    _state.update { state ->
                        state.copy(
                            avatar = user.avatar,
                        )
                    }
                }
            }

            ProfileEvent.NotificationToggle -> {
                userRepository.setNotificationActive(!_state.value.notificationActive)
            }

            ProfileEvent.PolicyClicked -> {
                _effect.send(ProfileEffect.ShowSnackBar("Не реализовано"))
            }

            ProfileEvent.TermsOfServesClicked -> {
                _effect.send(ProfileEffect.ShowSnackBar("Не реализовано"))
            }

            ProfileEvent.LogoutClicked -> {
                userRepository.logout()
                _effect.send(ProfileEffect.GoToLogin)
            }

        }
    }
}
