package com.plouton.CiviLized.data.repository

import com.plouton.CiviLized.data.api.ApiService
import com.plouton.CiviLized.data.model.AuthRequest
import com.plouton.CiviLized.data.model.AuthResponse
import com.plouton.CiviLized.data.model.RegisterRequest
import com.plouton.CiviLized.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val apiService: ApiService
) {
    
    suspend fun login(email: String, password: String): Result<AuthResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val request = AuthRequest(email, password)
                val response = apiService.login(request)
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
    
    suspend fun register(username: String, email: String, password: String): Result<AuthResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val request = RegisterRequest(username, email, password)
                val response = apiService.register(request)
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
    
    suspend fun getProfile(token: String): Result<User> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getProfile("Bearer $token")
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
} 