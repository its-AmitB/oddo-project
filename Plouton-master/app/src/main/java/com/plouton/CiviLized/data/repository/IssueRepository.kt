package com.plouton.CiviLized.data.repository

import com.plouton.CiviLized.data.api.ApiService
import com.plouton.CiviLized.data.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class IssueRepository @Inject constructor(
    private val apiService: ApiService
) {
    
    suspend fun getIssues(
        status: String? = null,
        category: String? = null,
        lat: Double? = null,
        lng: Double? = null,
        radius: Int = 5000,
        page: Int = 1,
        limit: Int = 20
    ): Result<PaginatedResponse<Issue>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getIssues(status, category, lat, lng, radius, page, limit)
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
    
    suspend fun getNearbyIssues(lat: Double, lng: Double, radius: Int = 5000): Result<List<Issue>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getNearbyIssues(lat, lng, radius)
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
    
    suspend fun getIssueById(id: String): Result<Issue> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getIssueById(id)
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
    
    suspend fun createIssue(
        title: String,
        description: String,
        category: String,
        lat: Double,
        lng: Double,
        address: String = "",
        anonymous: Boolean = false,
        images: List<File> = emptyList(),
        token: String? = null
    ): Result<Issue> {
        return withContext(Dispatchers.IO) {
            try {
                val titleBody = title.toRequestBody("text/plain".toMediaTypeOrNull())
                val descriptionBody = description.toRequestBody("text/plain".toMediaTypeOrNull())
                val categoryBody = category.toRequestBody("text/plain".toMediaTypeOrNull())
                val latBody = lat.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                val lngBody = lng.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                val addressBody = address.toRequestBody("text/plain".toMediaTypeOrNull())
                val anonymousBody = anonymous.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                
                val imageParts = images.map { file ->
                    val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
                    MultipartBody.Part.createFormData("images", file.name, requestBody)
                }
                
                val response = apiService.createIssue(
                    token?.let { "Bearer $it" },
                    titleBody,
                    descriptionBody,
                    categoryBody,
                    latBody,
                    lngBody,
                    addressBody,
                    anonymousBody,
                    imageParts
                )
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
    
    suspend fun updateIssueStatus(
        issueId: String,
        status: String,
        note: String = "",
        token: String
    ): Result<Issue> {
        return withContext(Dispatchers.IO) {
            try {
                val request = UpdateStatusRequest(status, note)
                val response = apiService.updateIssueStatus("Bearer $token", issueId, request)
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
    
    suspend fun flagIssue(issueId: String, reason: String, token: String? = null): Result<ApiResponse<String>> {
        return withContext(Dispatchers.IO) {
            try {
                val request = FlagIssueRequest(reason)
                val response = apiService.flagIssue(token?.let { "Bearer $it" }, issueId, request)
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
} 