package com.example.mystoreadmin.domain.useCases

import android.net.Uri
import com.example.mystoreadmin.domain.repo.Repo
import javax.inject.Inject

class AddCategoryPhotoUseCase @Inject constructor(private  val repo: Repo) {
    suspend operator fun invoke(photoUri: Uri) = repo.addCategoryPhoto(photoUri)
}