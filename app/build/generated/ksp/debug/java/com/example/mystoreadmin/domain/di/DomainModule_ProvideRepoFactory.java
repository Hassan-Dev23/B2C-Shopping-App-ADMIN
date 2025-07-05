package com.example.mystoreadmin.domain.di;

import com.example.mystoreadmin.domain.repo.Repo;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class DomainModule_ProvideRepoFactory implements Factory<Repo> {
  private final Provider<FirebaseFirestore> fireStoreProvider;

  private final Provider<FirebaseStorage> storageProvider;

  public DomainModule_ProvideRepoFactory(Provider<FirebaseFirestore> fireStoreProvider,
      Provider<FirebaseStorage> storageProvider) {
    this.fireStoreProvider = fireStoreProvider;
    this.storageProvider = storageProvider;
  }

  @Override
  public Repo get() {
    return provideRepo(fireStoreProvider.get(), storageProvider.get());
  }

  public static DomainModule_ProvideRepoFactory create(
      Provider<FirebaseFirestore> fireStoreProvider, Provider<FirebaseStorage> storageProvider) {
    return new DomainModule_ProvideRepoFactory(fireStoreProvider, storageProvider);
  }

  public static Repo provideRepo(FirebaseFirestore fireStore, FirebaseStorage storage) {
    return Preconditions.checkNotNullFromProvides(DomainModule.INSTANCE.provideRepo(fireStore, storage));
  }
}
