package com.example.mystoreadmin.domain.repo

import android.net.Uri
import com.example.mystoreadmin.common.ResultState
import com.example.mystoreadmin.domain.models.CategoryModel
import com.example.mystoreadmin.domain.models.Product
import kotlinx.coroutines.flow.Flow

interface Repo {
    suspend fun addCategory(categoryModel: CategoryModel): Flow<ResultState<String>>
    suspend fun addProduct(product: Product):Flow<ResultState<String>>
    suspend fun getAllCategories(): Flow<ResultState<List<CategoryModel>>>
    suspend fun addProductPhotos(photoUri:List<Uri>):Flow<ResultState<String>>
}