package com.example.mystoreadmin.domain.useCases;

import com.example.mystoreadmin.domain.repo.Repo;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class AddCategoryUseCase_Factory implements Factory<AddCategoryUseCase> {
  private final Provider<Repo> repoProvider;

  public AddCategoryUseCase_Factory(Provider<Repo> repoProvider) {
    this.repoProvider = repoProvider;
  }

  @Override
  public AddCategoryUseCase get() {
    return newInstance(repoProvider.get());
  }

  public static AddCategoryUseCase_Factory create(Provider<Repo> repoProvider) {
    return new AddCategoryUseCase_Factory(repoProvider);
  }

  public static AddCategoryUseCase newInstance(Repo repo) {
    return new AddCategoryUseCase(repo);
  }
}
