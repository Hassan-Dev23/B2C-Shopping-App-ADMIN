package com.example.mystoreadmin.domain.useCases

import com.example.mystoreadmin.common.ResultState
import com.example.mystoreadmin.domain.models.CategoryModel
import com.example.mystoreadmin.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddCategoryUseCase @Inject constructor( private val repo: Repo){
    suspend fun addCategoryUseCase(categoryModel: CategoryModel): Flow<ResultState<String>> {
        return repo.addCategory(categoryModel)
    }



}