package com.bksd.brandapp.core_ui

import androidx.lifecycle.ViewModel
import com.bksd.brandapp.extension.simpleLaunch
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

/**
 * Base ViewModel class that provides common functionality for managing UI state and effects.
 *
 * @param S The type of UI state.
 * @param E The type of events that the ViewModel can handle.
 * @param SE The type of side effects (one-time events like navigation).
 * @param initialState The initial UI state.
 */
abstract class BaseViewModel<S, E, SE>(initialState: S) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    open val state: StateFlow<S> = _state.asStateFlow()

    private val _effect = Channel<SE>(Channel.BUFFERED)
    open val effect: Flow<SE> = _effect.receiveAsFlow()

    abstract fun onEvent(event: E)

    protected fun updateState(reducer: S.() -> S) {
        val currentState = _state.value
        val newState = currentState.reducer()
        _state.tryEmit(newState)
    }

    /*protected fun updateState(reducer: S.() -> S) {
        _state.update { currentState ->
            currentState.reducer()
        }
    }*/

    protected fun sendEffect(effect: SE) = simpleLaunch {
        _effect.send(effect)
    }
}