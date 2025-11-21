package org.example.coinwatcher.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * The Base ViewModel for MVI Architecture.
 * All feature ViewModels must extend this class.
 *
 * @param STATE The class representing the screen state.
 * @param INTENT The class representing user actions.
 * @param EFFECT The class representing one-time side effects.
 */
abstract class MviViewModel<STATE : UiState, INTENT : UiIntent, EFFECT : UiEffect>(
    initialState: STATE
) : ViewModel() {

    // -------------------------------------------------------
    // 1. STATE MANAGEMENT
    // -------------------------------------------------------

    // Mutable State - only this class can change it
    private val _state = MutableStateFlow(initialState)

    // Immutable State - the UI observes this
    val state: StateFlow<STATE> = _state.asStateFlow()

    // -------------------------------------------------------
    // 2. EFFECT MANAGEMENT (One-time events)
    // -------------------------------------------------------

    // Channel is perfect for "Single Live Events"
    private val _effect = Channel<EFFECT>()

    // Exposed as a hot flow
    val effect = _effect.receiveAsFlow()

    // -------------------------------------------------------
    // 3. INTENT HANDLING (Input)
    // -------------------------------------------------------

    // This is the only entry point for the UI to talk to the ViewModel
    abstract fun onIntent(intent: INTENT)

    // -------------------------------------------------------
    // 4. HELPER FUNCTIONS
    // -------------------------------------------------------

    /**
     * Use this to update the state safely.
     * Example: setState { copy(isLoading = true) }
     */
    protected fun setState(reduce: STATE.() -> STATE) {
        _state.update(reduce)
    }

    /**
     * Use this to send a one-time event to the UI.
     * Example: setEffect { UiEffect.ShowError("Network Failed") }
     */
    protected fun setEffect(builder: () -> EFFECT) {
        viewModelScope.launch {
            _effect.send(builder())
        }
    }
}