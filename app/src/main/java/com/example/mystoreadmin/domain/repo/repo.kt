package com.example.mystoreadmin.domain.repo

import com.example.mystoreadmin.common.ResultState
import com.example.mystoreadmin.domain.models.CategoryModel
import kotlinx.coroutines.flow.Flow

interface Repo {
    suspend fun addCategory(categoryModel: CategoryModel): Flow<ResultState<String>>
}