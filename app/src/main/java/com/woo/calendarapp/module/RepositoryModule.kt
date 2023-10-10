package com.woo.calendarapp.module

import com.woo.calendarapp.repository.Repository
import com.woo.calendarapp.repository.RepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Qualifier

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

  @Binds
  @RepositoryRoom
  abstract fun bindRepository(
    repoImpl: RepositoryImpl
  ): Repository



}

@Qualifier
annotation class RepositoryRoom


