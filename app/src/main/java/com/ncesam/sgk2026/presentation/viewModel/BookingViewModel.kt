package com.ncesam.sgk2026.presentation.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ncesam.sgk2026.data.utils.validateDate
import com.ncesam.sgk2026.data.utils.validatePhone
import com.ncesam.sgk2026.domain.models.CartItemForm
import com.ncesam.sgk2026.domain.navigation.AppRoute
import com.ncesam.sgk2026.domain.repository.AppSettingsRepository
import com.ncesam.sgk2026.domain.repository.HotelRepository
import com.ncesam.sgk2026.domain.repository.ShopCartRepository
import com.ncesam.sgk2026.domain.state.BookingEffect
import com.ncesam.sgk2026.domain.state.BookingEvent
import com.ncesam.sgk2026.domain.state.BookingState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookingViewModel(
    savedStateHandle: SavedStateHandle,
    private val shopCartRepository: ShopCartRepository,
    private val hotelRepository: HotelRepository,
    private val appSettingsRepository: AppSettingsRepository
) : ViewModel() {
    private val _state = MutableStateFlow(BookingState())
    val state = _state.asStateFlow()

    private val booking = savedStateHandle.toRoute<AppRoute.Booking>()

    init {
        viewModelScope.launch {
            hotelRepository.getHotel(booking.hotelId).onSuccess { hotel ->
                val userId = appSettingsRepository.userIdFlow.first()
                _state.update { state -> state.copy(hotel = hotel, userId = userId) }
            }.onFailure { _effect.send(BookingEffect.GoToMain) }
        }
    }

    private val _effect = Channel<BookingEffect>()
    val effect = _effect.receiveAsFlow()

    suspend fun onEvent(event: BookingEvent) {
        when (event) {
            is BookingEvent.NameBookedChanged -> {
                _state.update { state -> state.copy(nameBooked = event.value.trim()) }
            }

            is BookingEvent.DateFromChanged -> {
                _state.update { state -> state.copy(dateFrom = event.value.trim()) }
            }

            is BookingEvent.DateToChanged -> {
                _state.update { state -> state.copy(dateTo = event.value.trim()) }
            }

            is BookingEvent.PhoneChanged -> {
                _state.update { state -> state.copy(phone = event.value.trim()) }
            }

            BookingEvent.AddToCart -> {
                val dateToError =
                    if (validateDate(_state.value.dateTo) == null) "Пример 2026-02-14" else null
                val dateFromError =
                    if (validateDate(_state.value.dateFrom) == null) "Пример 2026-02-14" else null
                val phoneError = if (!validatePhone(_state.value.phone)) "Телефон РФ" else null

                if (listOf(
                        phoneError,
                        dateToError,
                        dateFromError
                    ).any { value -> !value.isNullOrBlank() }
                ) {
                    _state.update { state ->
                        state.copy(
                            dateToError = dateToError,
                            dateFromError = dateFromError,
                            phoneError = phoneError,
                            dateTo = "",
                            dateFrom = "",
                            phone = "",
                        )
                    }
                    return
                } else {
                    val item = CartItemForm(
                        _state.value.hotel?.title ?: return,
                        1,
                        _state.value.hotel?.id ?: return,
                        _state.value.userId,
                        _state.value.hotel?.cost ?: return,
                        _state.value.dateFrom,
                        _state.value.dateTo,
                        _state.value.phone,
                        _state.value.nameBooked
                    )
                    shopCartRepository.addShopCart(item)
                    _effect.send(BookingEffect.GoToMain)
                }
            }
        }
    }
}