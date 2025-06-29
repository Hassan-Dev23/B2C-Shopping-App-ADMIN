package com.example.mystoreadmin.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mystoreadmin.common.ResultState
import com.example.mystoreadmin.common.ResultState.*
import com.example.mystoreadmin.domain.models.CategoryModel
import com.example.mystoreadmin.domain.useCases.AddCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class MyViewModel @Inject constructor(
    private val addCategoryUseCase: AddCategoryUseCase
) : ViewModel(){
//    Ui States
   private val _addCategoryState = MutableStateFlow<UiState<String>>(UiState.Empty)
    val addCategoryState = _addCategoryState.asStateFlow()










//    Functions for Ui States
    fun addCategory(category: CategoryModel){
        viewModelScope.launch {
            addCategoryUseCase.addCategoryUseCase(category).collect {
                when(it){
                    is ResultState.Error -> {
                        _addCategoryState.value = UiState.Error(it.message)
                    }
                    is ResultState.Loading -> {
                        _addCategoryState.value = UiState.Loading
                    }
                    is ResultState.Success<*> -> {
                        _addCategoryState.value = UiState.Success(it.data as String)
                    }
                    is ResultState.Empty -> {}
                }
            }
        }
    }

}


sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
    object Empty : UiState<Nothing>()

}