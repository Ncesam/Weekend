package com.ncesam.sgk2026.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ncesam.sgk2026.domain.repository.ShopCartRepository
import com.ncesam.sgk2026.domain.state.TravelsEffect
import com.ncesam.sgk2026.domain.state.TravelsEvent
import com.ncesam.sgk2026.domain.state.TravelsState
import com.ncesam.sgk2026.domain.useCase.GetAllHotelUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class TravelsViewModel(
    private val getAllHotelUseCase: GetAllHotelUseCase,
    private val shopCartRepository: ShopCartRepository
) : ViewModel() {

    private val _state = MutableStateFlow(TravelsState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            shopCartRepository.getAllItem().onSuccess { flow ->
                flow.collect { items ->
                    val hotelIds = items.map { item -> item.hotel }
                    getAllHotelUseCase().onSuccess { hotels ->
                        _state.update { state -> state.copy(items = hotels.filter { hotel -> hotel.id in hotelIds }) }
                    }
                }
            }
        }
    }


    private val _effect = Channel<TravelsEffect>()
    val effect = _effect.receiveAsFlow()

    suspend fun onEvent(event: TravelsEvent) {
        when (event) {
            TravelsEvent.AddClicked -> {
                _effect.send(TravelsEffect.ShowSnackBar("Не реализовано"))
            }

            is TravelsEvent.DeleteTravel -> {
                val cartItem = shopCartRepository.getAllItem().getOrThrow().first()
                    .find { item -> item.hotel == event.id } ?: return
                shopCartRepository.delete(cartItem.id)
            }
        }
    }

}