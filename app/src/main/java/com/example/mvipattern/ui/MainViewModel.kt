package com.example.mvipattern.ui

import androidx.lifecycle.*
import com.example.mvipattern.model.Blog
import com.example.mvipattern.repository.MainRepository
import com.example.mvipattern.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val mainRepository: MainRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _dataState: MutableLiveData<DataState<List<Blog>>> = MutableLiveData()

    val dataState: LiveData<DataState<List<Blog>>>
        get() = _dataState

    fun setStateEvent(mainStateEvent: MainStateEvent) {
        viewModelScope.launch {
            when (mainStateEvent) {
                is MainStateEvent.GetBlogsEvent -> {
                    mainRepository.getBlogs()
                        .onEach { dataState ->
                            _dataState.value = dataState
                        }
                        .launchIn(viewModelScope)
                }

                MainStateEvent.None -> {
                    // who cares
                }
            }
        }
    }

}


sealed class MainStateEvent {

    object GetBlogsEvent : MainStateEvent()

    object None : MainStateEvent()
}

