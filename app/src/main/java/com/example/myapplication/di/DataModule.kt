package com.example.myapplication.di

import com.example.myapplication.data.RepositoryImpl
import com.example.myapplication.data.usecases.GetCategoriesUsecaseImpl
import com.example.myapplication.data.usecases.GetCategoryProductsUsecaseImpl
import com.example.myapplication.data.usecases.GetProductUsecaseImpl
import com.example.myapplication.domain.Repository
import com.example.myapplication.domain.usecases.GetCategoriesUsecase
import com.example.myapplication.domain.usecases.GetCategoryProductsUsecase
import com.example.myapplication.domain.usecases.GetProductUsecase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface DataModule {
    @Binds
    @Singleton
    fun bindRepository(repo: RepositoryImpl): Repository

    @Binds
    @Singleton
    fun bindGetCategoriesUsecase(usecase: GetCategoriesUsecaseImpl): GetCategoriesUsecase

    @Binds
    @Singleton
    fun bindGetCategoryProductsUsecase(usecase: GetCategoryProductsUsecaseImpl): GetCategoryProductsUsecase
    @Binds
    @Singleton
    fun bindGetProductUsecase(usecase: GetProductUsecaseImpl): GetProductUsecase
}
