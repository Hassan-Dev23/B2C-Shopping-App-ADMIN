package com.example.mystoreadmin.data.repositoryImpl

import android.net.Uri
import android.widget.Toast
import com.example.mystoreadmin.common.CATEGORY_PATH
import com.example.mystoreadmin.common.PRODUCT_PATH
import com.example.mystoreadmin.common.ResultState
import com.example.mystoreadmin.domain.models.CategoryModel
import com.example.mystoreadmin.domain.models.Product
import com.example.mystoreadmin.domain.repo.Repo
import com.google.api.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class RepoImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage
) : Repo {
    override suspend fun addCategory(categoryModel: CategoryModel): Flow<ResultState<String>> =
        callbackFlow {
            trySend(ResultState.Loading)
            try {
                firestore.collection(CATEGORY_PATH).add(categoryModel).addOnSuccessListener {
                    trySend(ResultState.Success("Category Added Successfully."))
                }.addOnFailureListener {
                    trySend(ResultState.Error(it.message.toString()))
                }
            } catch (e: Exception) {
                trySend(
                    ResultState.Error(
                        "Error Message : ${e.message.toString()}" + "\n" +
                                "Error Cause : ${e.cause.toString()}" + "\n" +
                                "Error StackTrace : ${e.stackTrace}"
                    )
                )
            }
            awaitClose {
                close()
            }
        }

    override suspend fun addProduct(product: Product): Flow<ResultState<String>> = callbackFlow {

        trySend(ResultState.Loading)
        try {
            firestore.collection(PRODUCT_PATH).add(product).addOnSuccessListener {
                trySend(ResultState.Success("Product Added Successfully."))
            }.addOnFailureListener {
                trySend(ResultState.Error(it.message.toString()))
            }
        } catch (e: Exception) {
            trySend(
                ResultState.Error(
                    "Error Message : ${e.message.toString()}" + "\n" +
                            "Error Cause : ${e.cause.toString()}" + "\n" +
                            "Error StackTrace : ${e.stackTrace}"
                )
            )
        }
        awaitClose {
            close()
        }
    }

    override suspend fun getAllCategories(): Flow<ResultState<List<CategoryModel>>> = callbackFlow {
        trySend(ResultState.Loading)
        try {
            firestore.collection(CATEGORY_PATH).get().addOnSuccessListener {
                val categories = it.documents.mapNotNull { documentSnapshot ->
                    documentSnapshot.toObject(CategoryModel::class.java)
                }

                trySend(ResultState.Success(categories))
            }.addOnFailureListener {
                trySend(ResultState.Error(it.message.toString()))
            }
        } catch (e: Exception) {
            trySend(
                ResultState.Error(
                    "Error Message : ${e.message.toString()}" + "\n" +
                            "Error Cause : ${e.cause.toString()}" + "\n" +
                            "Error StackTrace : ${e.stackTrace}"
                )
            )
        }
        awaitClose {
            close()
        }
    }

    //    override suspend fun addProductPhotos(photoUri: Uri): Flow<ResultState<String>> =
//        callbackFlow {
//            trySend(ResultState.Loading)
//            try {
//                System.currentTimeMillis()
//                storage.reference.child("products/${System.currentTimeMillis()}")
//                    .putFile(photoUri).addOnSuccessListener {
//
//                        it.storage.downloadUrl.addOnSuccessListener {
//                            trySend(ResultState.Success(it.toString()))
//                        }.addOnFailureListener {
//                            trySend(
//                                ResultState.Error(
//                                    "Error Message : ${it.message.toString()}" + "\n" +
//                                            "Error Cause : ${it.cause.toString()}" + "\n" +
//                                            "Error StackTrace : ${it.stackTrace}"
//                                )
//                            )
//                        }
//                    }.addOnFailureListener {
//                        trySend(
//                            ResultState.Error(
//                                "Error Message : ${it.message.toString()}" + "\n" +
//                                        "Error Cause : ${it.cause.toString()}" + "\n" +
//                                        "Error StackTrace : ${it.stackTrace}"
//                            )
//                        )
//                    }
//
//            } catch (e: Exception) {
//                trySend(
//                    ResultState.Error(
//                        "Error Message : ${e.message.toString()}" + "\n" +
//                                "Error Cause : ${e.cause.toString()}" + "\n" +
//                                "Error StackTrace : ${e.stackTrace}"
//                    )
//                )
//            }
//        }
//    Same function from Chatgpt for list of uri
    override suspend fun addProductPhotos(photoUris: List<Uri>): Flow<ResultState<List<String>>> =
        callbackFlow {
            trySend(ResultState.Loading)

            val downloadUrls = mutableListOf<String>()

            val storageRef = storage.reference

            try {
                photoUris.forEachIndexed { index, uri ->
                    val fileName = "products/${System.currentTimeMillis()}_$index"
                    val imageRef = storageRef.child(fileName)

                    imageRef.putFile(uri)
                        .addOnSuccessListener { taskSnapshot ->
                            taskSnapshot.storage.downloadUrl
                                .addOnSuccessListener { downloadUrl ->
                                    downloadUrls.add(downloadUrl.toString())

                                    if (downloadUrls.size == photoUris.size) {
                                        trySend(ResultState.Success(downloadUrls))
                                        close()
                                    }
                                }
                                .addOnFailureListener { error ->
                                    trySend(ResultState.Error("Download URL Error: ${error.message}"))
                                    close()
                                }
                        }
                        .addOnFailureListener { error ->
                            trySend(ResultState.Error("Upload Error: ${error.message}"))
                            close()
                        }
                }
            } catch (e: Exception) {
                trySend(
                    ResultState.Error(
                        "Error Message : ${e.message}" +
                                "\nError Cause : ${e.cause}" +
                                "\nError StackTrace : ${e.stackTraceToString()}"
                    )
                )
                close()
            }

            awaitClose {}
        }


}