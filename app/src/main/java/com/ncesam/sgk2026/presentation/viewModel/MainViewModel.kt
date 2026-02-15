package com.ncesam.sgk2026.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ncesam.sgk2026.domain.repository.ShopCartRepository
import com.ncesam.sgk2026.domain.state.MainEffect
import com.ncesam.sgk2026.domain.state.MainEvent
import com.ncesam.sgk2026.domain.state.MainState
import com.ncesam.sgk2026.domain.useCase.GetAllHotelUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class MainViewModel(
    private val getAllHotelUseCase: GetAllHotelUseCase,
    private val shopCartRepository: ShopCartRepository
) : ViewModel() {
    private val _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()

    private val _effect = Channel<MainEffect>()
    val effect = _effect.receiveAsFlow()

    init {
        viewModelScope.launch {

            val hotelsResult = getAllHotelUseCase()

            if (hotelsResult.isFailure) {
                _effect.send(MainEffect.ShowSnackBar("Проверьте подключение к интернету."))
                return@launch
            }

            val hotels = hotelsResult.getOrThrow()

            shopCartRepository.getAllItem()
                .getOrThrow()
                .collect { items ->

                    val addedHotels = items.map { it.hotel }
                    val total = items.sumOf { it.perItem * it.quantity }

                    _state.update { state ->
                        state.copy(
                            allHotels = hotels,
                            filteredHotels = hotels,
                            categories = listOf("Баскетбол", "OutDoor"),
                            addedHotels = addedHotels,
                            totalShopCart = total
                        )
                    }
                }
        }
    }

    suspend fun onEvent(event: MainEvent) {
        when (event) {
            MainEvent.GoToCart -> {
                _effect.send(MainEffect.GoToCart)
            }
            is MainEvent.SearchChanged -> {
                _state.update { state -> state.copy(search = event.value) }
            }

            is MainEvent.AddToCart -> {
                val hotel = _state.value.allHotels.find { hotel -> hotel.id == event.id }
                if (hotel == null) {
                    _effect.send(MainEffect.ShowSnackBar("Нету такого ID"))
                    return
                }
                _effect.send(MainEffect.GoToBooking(hotel))
            }

            is MainEvent.SelectHotel -> {
                if (event.id.isBlank()) {
                    _state.update { state -> state.copy(selectedHotel = null) }
                    return
                }
                val hotel = _state.value.allHotels.find { hotel -> hotel.id == event.id }
                _state.update { state -> state.copy(selectedHotel = hotel) }
            }

            is MainEvent.SelectCategory -> {
                _state.update { state ->
                    state.copy(
                        selectedCategory = event.value,
                        filteredHotels = state.allHotels.filter { hotel -> hotel.category == event.value })
                }
            }

            is MainEvent.GoToSearch -> {
                _effect.send(MainEffect.GoToSearch(_state.value.search))
            }

            is MainEvent.GoToProfile -> {
                _effect.send(MainEffect.GoToProfile)
            }
        }
    }
}