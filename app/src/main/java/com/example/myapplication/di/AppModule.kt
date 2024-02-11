package com.example.myapplication.di

import com.example.myapplication.data.api.ShopApi
import com.example.myapplication.data.api.ShopApiHelper
import com.example.myapplication.data.api.ShopApiHelperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module(includes = [DataModule::class])
class AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): ShopApi {
        return retrofit.create(ShopApi::class.java)
    }

    @Provides
    @Singleton
    fun provideApiHelper(api: ShopApi): ShopApiHelper {
        return ShopApiHelperImpl(api)
    }
}
