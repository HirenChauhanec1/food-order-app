package com.codewithhiren.foodorderapplatest.di

import com.codewithhiren.foodorderapplatest.repository.Repository
import com.codewithhiren.foodorderapplatest.repository.RepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

   @ViewModelScoped
   @Binds
   abstract fun getRepository(repositoryImpl: RepositoryImpl) : Repository

}