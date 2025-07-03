package com.example.mystoreadmin.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mystoreadmin.common.ResultState
import com.example.mystoreadmin.common.ResultState.*
import com.example.mystoreadmin.domain.models.CategoryModel
import com.example.mystoreadmin.domain.models.Product
import com.example.mystoreadmin.domain.useCases.AddCategoryUseCase
import com.example.mystoreadmin.domain.useCases.AddProductUseCase
import com.example.mystoreadmin.domain.useCases.GetAllCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class MyViewModel @Inject constructor(
    private val addCategoryUseCase: AddCategoryUseCase,
    private val addProductUseCase: AddProductUseCase,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase
) : ViewModel() {
    //    Ui States
    private val _addCategoryState = MutableStateFlow<UiState<String>>(UiState.Empty)
    val addCategoryState = _addCategoryState.asStateFlow()

    private val _addProductState = MutableStateFlow<UiState<String>>(UiState.Empty)
    val addProductState = _addProductState.asStateFlow()
    private val _getAllCategoriesState = MutableStateFlow<UiState<List<CategoryModel>>>(UiState.Empty)
    val getAllCategoriesState = _getAllCategoriesState.asStateFlow()


    //    Functions for Ui States
    fun addCategory(category: CategoryModel) {
        viewModelScope.launch {
            addCategoryUseCase.addCategoryUseCase(category).collect {
                when (it) {
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


    fun addProduct(product: Product) {
        viewModelScope.launch {
            addProductUseCase.addProductUseCase(product).collect {
                when (it) {
                    is ResultState.Error -> {
                        _addProductState.value = UiState.Error(it.message)
                    }

                    is ResultState.Loading -> {
                        _addProductState.value = UiState.Loading
                    }

                    is ResultState.Success<*> -> {
                        _addProductState.value = UiState.Success(it.data as String)
                    }

                    is ResultState.Empty -> {}
                }
            }

        }
    }
    fun getAllCategories() {
        viewModelScope.launch {
            getAllCategoriesUseCase.getAllCategoriesUseCase().collect {
                when (it) {
                    is ResultState.Error -> {
                        _getAllCategoriesState.value = UiState.Error(it.message)
                    }

                    is ResultState.Loading -> {
                        _getAllCategoriesState.value = UiState.Loading
                    }

                    is ResultState.Success<*> -> {
                        _getAllCategoriesState.value = UiState.Success(it.data as List<CategoryModel>)
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