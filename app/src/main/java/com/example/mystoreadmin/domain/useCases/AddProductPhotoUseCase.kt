package com.example.mystoreadmin.domain.useCases

import android.net.Uri
import com.example.mystoreadmin.domain.repo.Repo
import jakarta.inject.Inject

class AddProductPhotoUseCase @Inject constructor(private val repo: Repo) {
    suspend operator fun invoke(photoUris: List<Uri>) = repo.addProductPhotos(photoUris)
}