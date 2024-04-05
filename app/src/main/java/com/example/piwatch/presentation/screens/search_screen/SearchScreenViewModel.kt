package com.example.piwatch.presentation.screens.search_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.piwatch.domain.usecase.movie_usecase.GetSearchedMoviesUseCase
import com.example.piwatch.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val getSearchedMoviesUseCase: GetSearchedMoviesUseCase
): ViewModel() {

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    private val _searchScrenState = MutableStateFlow(SearchScreenState())
    val searchScrenState = _searchScrenState.asStateFlow()

    var page = 1

    private val queryDebounce = MutableStateFlow("")


    init {
        viewModelScope.launch {
            query.debounce(1000) // Thời gian chờ debounce là 300ms (có thể điều chỉnh)
                .collectLatest { query ->
                    queryDebounce.emit(query)
                }
        }
        observeQueryDebounce()
    }

    suspend fun onEvent(event: SearchScreenEvent){
        when(event){
            is SearchScreenEvent.QueryChange -> {
                _query.value = event.query
            }
            is SearchScreenEvent.nextPage -> {
                page++;
                getSearchedMovies(_query.value, page)
            }
            is SearchScreenEvent.previousPage -> {
                page--;
                getSearchedMovies(_query.value, page)
            }
        }
    }

    private fun observeQueryDebounce() {
        viewModelScope.launch(IO) {
            queryDebounce.collectLatest { debouncedQuery ->
                if (debouncedQuery.isNotEmpty()) {
                    getSearchedMovies(debouncedQuery)
                }
            }
        }
    }

    suspend fun getSearchedMovies(query: String, page: Int = 1){
        viewModelScope.launch(IO) {
            getSearchedMoviesUseCase.execute(query, page).collect{result ->
                Log.d("search", "${result.message}\n$page")
                when(result){
                    is Resource.Success -> {
                        result.result?.let {
                            _searchScrenState.value = _searchScrenState.value.copy(
                                movieList = it,
                                isLoading = false,
                                isError = false,
                            )
                            if(it.isEmpty()){
                                _searchScrenState.value = _searchScrenState.value.copy(
                                    isLoading = false,
                                    isError = true,
                                )
                            }
                        }
                    }
                    is Resource.Loading -> {
                        _searchScrenState.value = _searchScrenState.value.copy(
                            isLoading = true
                        )
                    }
                    is Resource.Error -> {
                        _searchScrenState.value = _searchScrenState.value.copy(
                            isError = true,
                            isLoading = false
                        )
                    }
                }

            }
        }
    }
}