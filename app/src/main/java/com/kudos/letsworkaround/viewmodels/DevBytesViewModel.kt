package com.kudos.letsworkaround.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kudos.letsworkaround.network.models.DevBytesVideoApi
import com.kudos.letsworkaround.repository.MainRepository
import com.kudos.letsworkaround.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class DevBytesViewModel @Inject constructor(private val mainRepository: MainRepository) :
    ViewModel() {

    private val _eventNetworkError = MutableLiveData<Boolean>()
    val eventNetworkError: LiveData<Boolean> = _eventNetworkError

    private val _isNetworkErrorShown = MutableLiveData<Boolean>()
    val isNetworkErrorShown: LiveData<Boolean> = _isNetworkErrorShown

    private val _playlist = MutableLiveData<Resource<List<DevBytesVideoApi>>>()
    val playlist: LiveData<Resource<List<DevBytesVideoApi>>> = _playlist

    fun getPlaylist() {
        viewModelScope.launch {
            try {
                val response = mainRepository.getPlaylist()
                _playlist.value = Resource.success(response.videos)
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false

            } catch (networkError: IOException) {
                // Show a Toast error message and hide the progress bar.
                _playlist.value = Resource.error(networkError.toString())
                _eventNetworkError.value = true
            }
        }
    }

}