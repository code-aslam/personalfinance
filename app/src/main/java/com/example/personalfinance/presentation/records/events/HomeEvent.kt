package com.example.personalfinance.presentation.records.events

sealed interface HomeEvent {
    data object showDialog : HomeEvent
    data object hideDialog : HomeEvent
}