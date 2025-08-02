package com.plouton.CiviLized.di

import com.plouton.CiviLized.data.repository.AuthRepository
import com.plouton.CiviLized.data.repository.IssueRepository
import com.plouton.CiviLized.data.repository.CategoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    
    @Provides
    @Singleton
    fun provideAuthRepository(apiService: com.plouton.CiviLized.data.api.ApiService): AuthRepository {
        return AuthRepository(apiService)
    }
    
    @Provides
    @Singleton
    fun provideIssueRepository(apiService: com.plouton.CiviLized.data.api.ApiService): IssueRepository {
        return IssueRepository(apiService)
    }
    
    @Provides
    @Singleton
    fun provideCategoryRepository(apiService: com.plouton.CiviLized.data.api.ApiService): CategoryRepository {
        return CategoryRepository(apiService)
    }
} 