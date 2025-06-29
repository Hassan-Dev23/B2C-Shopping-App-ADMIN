package com.example.mystoreadmin.data.repositoryImpl

import com.example.mystoreadmin.common.CATEGORY_PATH
import com.example.mystoreadmin.common.ResultState
import com.example.mystoreadmin.domain.models.CategoryModel
import com.example.mystoreadmin.domain.repo.Repo
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import jakarta.inject.Inject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class RepoImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage
): Repo {
    override suspend fun addCategory(categoryModel: CategoryModel): Flow<ResultState<String>> =
        callbackFlow {
            trySend(ResultState.Loading)
            try {
                firestore.collection(CATEGORY_PATH).add(categoryModel).addOnSuccessListener {
                    trySend(ResultState.Success("Category Added Successfully"))
                }.addOnFailureListener {
                    trySend(ResultState.Error(it.message.toString()))
                }
            } catch (e: Exception) {
                trySend(ResultState.Error(e.message.toString()))
            }
            awaitClose {
                close()
            }
        }
}