package com.ncesam.sgk2026.presentation.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ncesam.sgk2026.domain.navigation.AppRoute
import com.ncesam.sgk2026.domain.state.SearchEffect
import com.ncesam.sgk2026.domain.state.SearchEvent
import com.ncesam.sgk2026.domain.state.SearchState
import com.ncesam.sgk2026.domain.useCase.GetAllHotelUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class SearchViewModel(
    savedStateHandle: SavedStateHandle,
    private val getAllHotelsUseCase: GetAllHotelUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SearchState())
    val state = _state.asStateFlow()
    val search = savedStateHandle.toRoute<AppRoute.Search>()
    val searchValue = search.value

    init {
        viewModelScope.launch {
            getAllHotelsUseCase().onSuccess { hotels ->
                _state.update { state ->
                    state.copy(filteredHotels = if (searchValue.isNotEmpty()) hotels.filter { hotel -> searchValue in hotel.description } else emptyList(),
                        allHotels = hotels,
                        searchValue = searchValue)
                }
            }.onFailure {
                _effect.send(SearchEffect.ShowSnackBar("Проверьте интернет подключение "))
                return@launch
            }
        }
    }


    private val _effect = Channel<SearchEffect>()
    val effect = _effect.receiveAsFlow()

    suspend fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.SearchValueChanged -> {
                _state.update { state -> state.copy(searchValue = event.value) }
            }

            is SearchEvent.AddToCart -> {
                val hotel = _state.value.allHotels.find { hotel -> hotel.id == event.id }
                if (hotel == null) {
                    _effect.send(SearchEffect.ShowSnackBar("Нету такого ID"))
                    return
                }
                _effect.send(SearchEffect.GoToBooking(hotel))
            }

            is SearchEvent.SelectHotel -> {
                _state.update { state -> state.copy(selectedHotel = if (event.id.isBlank()) null else state.allHotels.find { hotel -> hotel.id == event.id }) }
            }

            is SearchEvent.GoToBooking -> {
                _effect.send(SearchEffect.GoToBooking(event.hotel))
            }

            SearchEvent.Search -> {
                _state.update { state -> state.copy(filteredHotels = _state.value.allHotels.filter { hotel -> _state.value.searchValue.lowercase().trim() in hotel.description.lowercase().trim() }) }
            }
        }
    }
}