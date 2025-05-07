package com.example.amphibiansapp.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.amphibiansapp.AmphibianApplication
import com.example.amphibiansapp.data.AmphibianRepository
import com.example.amphibiansapp.network.Amphibian
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface AmphibianUiState{
    data class Success(val amphibians: List<Amphibian>) : AmphibianUiState
    object Error: AmphibianUiState
    object Loading: AmphibianUiState
}

class AmphibianViewModel (private val amphibianRepository: AmphibianRepository) : ViewModel() {
    var amphibianUiState: AmphibianUiState by mutableStateOf(AmphibianUiState.Loading)
        private set

    init {
        getAmphibians()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory{
            initializer{
                val application = (this[APPLICATION_KEY]
                        as AmphibianApplication)
                val amphibianRepository = application.container.amphibianRepository
                AmphibianViewModel(amphibianRepository = amphibianRepository)
            }
        }
    }

    private fun getAmphibians() {
        viewModelScope.launch {
            amphibianUiState =  try{
                val listResult = amphibianRepository.getAmphibians()
                AmphibianUiState.Success(listResult)
            }
            catch (e: IOException){
                AmphibianUiState.Error
            }

        }
    }
}