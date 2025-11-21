package org.example.coinwatcher.core.base

/**
 * 1. UI STATE (View State)
 * Represents the current snapshot of the screen.
 * Example: Loading, Error, or a List of Coins.
 * The UI observes this and redraws itself when it changes.
 */
interface UiState

/**
 * 2. UI INTENT (User Action)
 * Represents an action triggered by the user.
 * Example: Clicking a button, Typing in search, Pulling to refresh.
 * The ViewModel processes these.
 */
interface UiIntent

/**
 * 3. UI EFFECT (Side Effect)
 * Represents a one-time event that doesn't persist.
 * Example: Navigation events, Show Toast, Show Snackbar, Vibrate.
 * The UI handles this once and forgets it.
 */
interface UiEffect