package com.ncesam.sgk2026.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.ncesam.sgk2026.domain.repository.ShopCartRepository
import com.ncesam.sgk2026.domain.state.ShopCartEffect
import com.ncesam.sgk2026.domain.state.ShopCartEvent
import com.ncesam.sgk2026.domain.state.ShopCartState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class ShopCartViewModel(private val shopCartRepository: ShopCartRepository) : ViewModel() {
    private val _state = MutableStateFlow(ShopCartState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            shopCartRepository.getAllItem().onSuccess { flow ->
                flow.collect { items ->
                    _state.update { state -> state.copy(items = items) }
                }
            }.onFailure { _effect.send(ShopCartEffect.ShowSnackBar("Не найдены элементы")) }
        }
    }

    private val _effect = Channel<ShopCartEffect>()
    val effect = _effect.receiveAsFlow()

    suspend fun onEvent(event: ShopCartEvent) {
        when (event) {
            is ShopCartEvent.Decrement -> {
                val cartItem = _state.value.items.find { item -> item.id == event.id } ?: return
                if (cartItem.quantity != 1) {
                    shopCartRepository.updateQuantity(cartItem.quantity - 1, cartItem.id)
                }
            }

            is ShopCartEvent.Increment -> {
                val cartItem = _state.value.items.find { item -> item.id == event.id } ?: return
                shopCartRepository.updateQuantity(cartItem.quantity + 1, cartItem.id)
            }

            is ShopCartEvent.Delete -> {
                val cartItem = _state.value.items.find { item -> item.id == event.id } ?: return
                shopCartRepository.delete(cartItem.id)
            }

            ShopCartEvent.DeleteAll -> {
                shopCartRepository.deleteAll()
                _effect.send(ShopCartEffect.ShowSnackBar("Все успешно удалено"))
            }

            ShopCartEvent.Buy -> {
                shopCartRepository.orderItems()
                _effect.send(ShopCartEffect.GoToMain)
            }

            ShopCartEvent.Back -> {
                _effect.send(ShopCartEffect.Back)
            }
        }
    }

}