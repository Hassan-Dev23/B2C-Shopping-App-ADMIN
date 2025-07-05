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
public final class GetAllCategoriesUseCase_Factory implements Factory<GetAllCategoriesUseCase> {
  private final Provider<Repo> repoProvider;

  public GetAllCategoriesUseCase_Factory(Provider<Repo> repoProvider) {
    this.repoProvider = repoProvider;
  }

  @Override
  public GetAllCategoriesUseCase get() {
    return newInstance(repoProvider.get());
  }

  public static GetAllCategoriesUseCase_Factory create(Provider<Repo> repoProvider) {
    return new GetAllCategoriesUseCase_Factory(repoProvider);
  }

  public static GetAllCategoriesUseCase newInstance(Repo repo) {
    return new GetAllCategoriesUseCase(repo);
  }
}
