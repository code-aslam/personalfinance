package com.hotdogcode.spendwise.presentation.records.events

sealed interface HomeEvent {
    data object showDialog : HomeEvent
    data object hideDialog : HomeEvent
}