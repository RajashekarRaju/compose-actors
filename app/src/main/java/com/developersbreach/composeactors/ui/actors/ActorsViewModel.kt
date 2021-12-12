package com.developersbreach.composeactors.ui.actors

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.developersbreach.composeactors.ComposeActorsApp
import com.developersbreach.composeactors.model.Actor
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ActorsViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository = (application as ComposeActorsApp).repository

    private var actors = listOf<Actor>()

    private var _uiState = MutableStateFlow(ActorsViewState())
    val uiState: StateFlow<ActorsViewState> = _uiState

    init {
        viewModelScope.launch {
            actors = repository.getActorsData()
            _uiState.value.actorsList = repository.getActorsData()
        }
    }

    fun getSelectedActor(
        actorId: Int
    ): Actor? {
        return actors.find {
            it.actorId == actorId
        }
    }
}

data class ActorsViewState(
    var actorsList: List<Actor> = emptyList()
)