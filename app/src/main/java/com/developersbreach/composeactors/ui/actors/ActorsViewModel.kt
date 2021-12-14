package com.developersbreach.composeactors.ui.actors

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.developersbreach.composeactors.ComposeActorsApp
import com.developersbreach.composeactors.model.Actor
import kotlinx.coroutines.launch

class ActorsViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository = (application as ComposeActorsApp).repository

    private var _actorsViewState = MutableLiveData(ActorsViewState())
    val actorsViewState: LiveData<ActorsViewState> = _actorsViewState

    init {
        viewModelScope.launch {
            val popularActorsList = repository.getPopularActorsData()
            val trendingActorsList = repository.getTrendingActorsData()
            _actorsViewState.postValue(
                ActorsViewState(popularActorsList, trendingActorsList)
            )
        }
    }
}

data class ActorsViewState(
    var popularActorList: List<Actor> = emptyList(),
    var trendingActorList: List<Actor> = emptyList()
)