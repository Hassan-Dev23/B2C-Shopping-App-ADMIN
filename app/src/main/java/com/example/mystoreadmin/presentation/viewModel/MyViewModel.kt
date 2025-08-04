package com.example.mystoreadmin.presentation.viewModel

import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mystoreadmin.common.ResultState
import com.example.mystoreadmin.common.ResultState.*
import com.example.mystoreadmin.domain.models.CategoryModel
import com.example.mystoreadmin.domain.models.Product
import com.example.mystoreadmin.domain.useCases.AddCategoryPhotoUseCase
import com.example.mystoreadmin.domain.useCases.AddCategoryUseCase
import com.example.mystoreadmin.domain.useCases.AddProductPhotoUseCase
import com.example.mystoreadmin.domain.useCases.AddProductUseCase
import com.example.mystoreadmin.domain.useCases.GetAllCategoriesUseCase
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class MyViewModel @Inject constructor(
    private val addCategoryUseCase: AddCategoryUseCase,
    private val addProductUseCase: AddProductUseCase,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val addProductPhotoUseCase: AddProductPhotoUseCase,
    private val addCategoryPhotoUseCase: AddCategoryPhotoUseCase
) : ViewModel() {
    //    Ui States
    private val _addCategoryState = MutableStateFlow<UiState<String>>(UiState.Empty)
    val addCategoryState = _addCategoryState.asStateFlow()

    private val _addProductState = MutableStateFlow<UiState<String>>(UiState.Empty)
    val addProductState = _addProductState.asStateFlow()
    private val _getAllCategoriesState = MutableStateFlow<UiState<List<CategoryModel>>>(UiState.Empty)
    val getAllCategoriesState = _getAllCategoriesState.asStateFlow()
    private val _addProductPhotosState = MutableStateFlow<UiState<List<String>>>(UiState.Empty)
    val addProductPhotosState = _addProductPhotosState.asStateFlow()
    private val _addCategoryPhotoState = MutableStateFlow<UiState<String>>(UiState.Empty)
    val addCategoryPhotoState = _addCategoryPhotoState.asStateFlow()



    fun resetUiStates(){
        _addProductState.value = UiState.Empty
        _addProductPhotosState.value = UiState.Empty
    }


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


    fun addCategoryPhoto(photoUri: Uri){
        viewModelScope.launch {
            addCategoryPhotoUseCase.invoke(photoUri).collect{

                when (it) {
                    is ResultState.Error -> {
                        _addCategoryPhotoState.value = UiState.Error(it.message)
                    }

                    is ResultState.Loading -> {
                        _addCategoryPhotoState.value = UiState.Loading
                    }

                    is ResultState.Success<*> -> {

                        _addCategoryPhotoState.value = UiState.Success(it.data as String)
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



    fun addProductPhotos(photoUris: List<Uri>){
        viewModelScope.launch {
            addProductPhotoUseCase.invoke(photoUris).collect{

                when (it) {
                    is ResultState.Error -> {
                        _addProductPhotosState.value = UiState.Error(it.message)
                    }

                    is ResultState.Loading -> {
                        _addProductPhotosState.value = UiState.Loading
                    }

                    is ResultState.Success<*> -> {

                        _addProductPhotosState.value = UiState.Success(it.data as List<String>)
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