package com.example.piwatch.presentation.screens.search_screen

import com.example.piwatch.presentation.screens.login_screen.LoginEvent
import com.google.firebase.auth.AuthCredential

sealed class SearchScreenEvent {
    data class QueryChange(val query: String): SearchScreenEvent()
    object nextPage: SearchScreenEvent()
    object previousPage: SearchScreenEvent()
}