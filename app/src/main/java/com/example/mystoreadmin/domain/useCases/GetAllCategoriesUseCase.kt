package com.example.mystoreadmin.domain.useCases

import com.example.mystoreadmin.common.ResultState
import com.example.mystoreadmin.domain.models.CategoryModel
import com.example.mystoreadmin.domain.repo.Repo
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetAllCategoriesUseCase @Inject constructor(private val repo: Repo) {
    suspend fun getAllCategoriesUseCase(): Flow<ResultState<List<CategoryModel>>> {
        return repo.getAllCategories()
    }
}