package com.example.mystoreadmin.domain.di

import com.example.mystoreadmin.data.repositoryImpl.RepoImpl
import com.example.mystoreadmin.domain.repo.Repo
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    fun provideRepo(fireStore: FirebaseFirestore, storage: FirebaseStorage): Repo {
        return RepoImpl(fireStore, storage )
    }

    

}