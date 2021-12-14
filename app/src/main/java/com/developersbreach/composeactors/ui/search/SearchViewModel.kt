package com.developersbreach.composeactors.ui.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.developersbreach.composeactors.ComposeActorsApp
import com.developersbreach.composeactors.model.Actor
import kotlinx.coroutines.launch

class SearchViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository = (application as ComposeActorsApp).repository

    private var _searchUiState = MutableLiveData<List<Actor>>()
    val searchUiState: LiveData<List<Actor>> = _searchUiState

    init {
        viewModelScope.launch {
            val searchData = repository.getSearchableActorsData("robert")
            _searchUiState.postValue(searchData)
        }
    }
}