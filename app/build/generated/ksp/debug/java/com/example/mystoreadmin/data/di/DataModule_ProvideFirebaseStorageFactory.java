package com.example.mystoreadmin.data.di;

import com.google.firebase.storage.FirebaseStorage;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class DataModule_ProvideFirebaseStorageFactory implements Factory<FirebaseStorage> {
  @Override
  public FirebaseStorage get() {
    return provideFirebaseStorage();
  }

  public static DataModule_ProvideFirebaseStorageFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static FirebaseStorage provideFirebaseStorage() {
    return Preconditions.checkNotNullFromProvides(DataModule.INSTANCE.provideFirebaseStorage());
  }

  private static final class InstanceHolder {
    static final DataModule_ProvideFirebaseStorageFactory INSTANCE = new DataModule_ProvideFirebaseStorageFactory();
  }
}
