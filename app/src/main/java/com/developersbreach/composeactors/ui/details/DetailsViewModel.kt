package com.developersbreach.composeactors.ui.details

import android.app.Application
import androidx.lifecycle.*
import com.developersbreach.composeactors.ComposeActorsApp
import com.developersbreach.composeactors.model.ActorDetail
import com.developersbreach.composeactors.model.Movie
import kotlinx.coroutines.launch


class DetailsViewModel(
    application: Application,
    private val actorId: Int
) : AndroidViewModel(application) {

    private val repository = (application as ComposeActorsApp).repository

    private var _uiState = MutableLiveData<ActorDetail>()
    val uiState: LiveData<ActorDetail> = _uiState

    private var _castState = MutableLiveData<List<Movie>>()
    val castState: LiveData<List<Movie>> = _castState

    init {
        viewModelScope.launch {
            val actorData = repository.getSelectedActorData(actorId)
            _uiState.postValue(actorData)

            val castData = repository.getCastData(actorId)
            _castState.postValue(castData)
        }
    }

    companion object {

        fun provideFactory(
            application: Application,
            actorId: Int
        ): ViewModelProvider.AndroidViewModelFactory {
            return object : ViewModelProvider.AndroidViewModelFactory(application) {
                @Suppress("unchecked_cast")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return DetailsViewModel(application, actorId) as T
                }
            }
        }
    }
}