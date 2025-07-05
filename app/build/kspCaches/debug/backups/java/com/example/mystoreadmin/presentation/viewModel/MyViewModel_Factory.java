package com.example.mystoreadmin.presentation.viewModel;

import com.example.mystoreadmin.domain.useCases.AddCategoryUseCase;
import com.example.mystoreadmin.domain.useCases.AddProductUseCase;
import com.example.mystoreadmin.domain.useCases.GetAllCategoriesUseCase;
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
public final class MyViewModel_Factory implements Factory<MyViewModel> {
  private final Provider<AddCategoryUseCase> addCategoryUseCaseProvider;

  private final Provider<AddProductUseCase> addProductUseCaseProvider;

  private final Provider<GetAllCategoriesUseCase> getAllCategoriesUseCaseProvider;

  public MyViewModel_Factory(Provider<AddCategoryUseCase> addCategoryUseCaseProvider,
      Provider<AddProductUseCase> addProductUseCaseProvider,
      Provider<GetAllCategoriesUseCase> getAllCategoriesUseCaseProvider) {
    this.addCategoryUseCaseProvider = addCategoryUseCaseProvider;
    this.addProductUseCaseProvider = addProductUseCaseProvider;
    this.getAllCategoriesUseCaseProvider = getAllCategoriesUseCaseProvider;
  }

  @Override
  public MyViewModel get() {
    return newInstance(addCategoryUseCaseProvider.get(), addProductUseCaseProvider.get(), getAllCategoriesUseCaseProvider.get());
  }

  public static MyViewModel_Factory create(Provider<AddCategoryUseCase> addCategoryUseCaseProvider,
      Provider<AddProductUseCase> addProductUseCaseProvider,
      Provider<GetAllCategoriesUseCase> getAllCategoriesUseCaseProvider) {
    return new MyViewModel_Factory(addCategoryUseCaseProvider, addProductUseCaseProvider, getAllCategoriesUseCaseProvider);
  }

  public static MyViewModel newInstance(AddCategoryUseCase addCategoryUseCase,
      AddProductUseCase addProductUseCase, GetAllCategoriesUseCase getAllCategoriesUseCase) {
    return new MyViewModel(addCategoryUseCase, addProductUseCase, getAllCategoriesUseCase);
  }
}
