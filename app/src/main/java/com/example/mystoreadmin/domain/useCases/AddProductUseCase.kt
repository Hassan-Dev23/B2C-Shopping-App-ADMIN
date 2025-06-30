package com.example.mystoreadmin.domain.useCases

import com.example.mystoreadmin.common.ResultState
import com.example.mystoreadmin.domain.models.Product
import com.example.mystoreadmin.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddProductUseCase @Inject  constructor(private val repo: Repo) {

    suspend fun addProductUseCase (product: Product) : Flow<ResultState<String>>{
        return repo.addProduct(product)
    }
}