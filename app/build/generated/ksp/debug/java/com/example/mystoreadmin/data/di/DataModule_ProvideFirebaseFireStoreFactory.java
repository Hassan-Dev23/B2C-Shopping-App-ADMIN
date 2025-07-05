package com.example.mystoreadmin.data.di;

import com.google.firebase.firestore.FirebaseFirestore;
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
public final class DataModule_ProvideFirebaseFireStoreFactory implements Factory<FirebaseFirestore> {
  @Override
  public FirebaseFirestore get() {
    return provideFirebaseFireStore();
  }

  public static DataModule_ProvideFirebaseFireStoreFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static FirebaseFirestore provideFirebaseFireStore() {
    return Preconditions.checkNotNullFromProvides(DataModule.INSTANCE.provideFirebaseFireStore());
  }

  private static final class InstanceHolder {
    static final DataModule_ProvideFirebaseFireStoreFactory INSTANCE = new DataModule_ProvideFirebaseFireStoreFactory();
  }
}
