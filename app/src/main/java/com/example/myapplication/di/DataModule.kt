package com.example.myapplication.di

import com.example.myapplication.data.RepositoryImpl
import com.example.myapplication.data.usecases.GetCategoriesUsecaseImpl
import com.example.myapplication.domain.Repository
import com.example.myapplication.domain.usecases.GetCategoriesUsecase
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
    abstract fun bindRepository(repo: RepositoryImpl): Repository

    @Binds
    @Singleton
    abstract fun bindGetCategoriesUsecase(usecase: GetCategoriesUsecaseImpl): GetCategoriesUsecase
}
