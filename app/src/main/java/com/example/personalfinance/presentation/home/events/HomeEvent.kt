package com.example.personalfinance.presentation.home.events

sealed interface HomeEvent {
    data object showDialog : HomeEvent
    data object hideDialog : HomeEvent
}