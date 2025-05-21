package com.codewithhiren.foodorderapplatest.di

import com.codewithhiren.foodorderapplatest.FoodApi
import com.codewithhiren.foodorderapplatest.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun getRetrofit() : Retrofit{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun getFoodApi(retrofit: Retrofit) : FoodApi{
        return retrofit.create(FoodApi::class.java)
    }

}