package com.plouton.CiviLized.data.repository

import com.plouton.CiviLized.data.api.ApiService
import com.plouton.CiviLized.data.model.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val apiService: ApiService
) {
    
    suspend fun getCategories(): Result<List<Category>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getCategories()
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
} 