package com.plouton.CiviLized.data.api

import com.plouton.CiviLized.data.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {
    
    // Authentication
    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): AuthResponse
    
    @POST("auth/login")
    suspend fun login(@Body request: AuthRequest): AuthResponse
    
    @GET("auth/profile")
    suspend fun getProfile(@Header("Authorization") token: String): User
    
    // Issues
    @POST("issues")
    suspend fun createIssue(
        @Header("Authorization") token: String? = null,
        @Part("title") title: RequestBody,
        @Part("description") description: RequestBody,
        @Part("category") category: RequestBody,
        @Part("lat") lat: RequestBody,
        @Part("lng") lng: RequestBody,
        @Part("address") address: RequestBody? = null,
        @Part("anonymous") anonymous: RequestBody? = null,
        @Part images: List<MultipartBody.Part>? = null
    ): Issue
    
    @GET("issues")
    suspend fun getIssues(
        @Query("status") status: String? = null,
        @Query("category") category: String? = null,
        @Query("lat") lat: Double? = null,
        @Query("lng") lng: Double? = null,
        @Query("radius") radius: Int = 5000,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): PaginatedResponse<Issue>
    
    @GET("issues/nearby")
    suspend fun getNearbyIssues(
        @Query("lat") lat: Double,
        @Query("lng") lng: Double,
        @Query("radius") radius: Int = 5000
    ): List<Issue>
    
    @GET("issues/{id}")
    suspend fun getIssueById(@Path("id") id: String): Issue
    
    @PATCH("issues/{id}/status")
    suspend fun updateIssueStatus(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body request: UpdateStatusRequest
    ): Issue
    
    @POST("issues/{id}/flag")
    suspend fun flagIssue(
        @Header("Authorization") token: String? = null,
        @Path("id") id: String,
        @Body request: FlagIssueRequest
    ): ApiResponse<String>
    
    // Categories
    @GET("categories")
    suspend fun getCategories(): List<Category>
    
    // Admin endpoints
    @GET("admin/issues/flagged")
    suspend fun getFlaggedIssues(@Header("Authorization") token: String): List<Issue>
    
    @POST("admin/ban-user")
    suspend fun banUser(
        @Header("Authorization") token: String,
        @Body request: Map<String, String>
    ): ApiResponse<String>
    
    @POST("admin/unban-user")
    suspend fun unbanUser(
        @Header("Authorization") token: String,
        @Body request: Map<String, String>
    ): ApiResponse<String>
    
    @GET("admin/analytics")
    suspend fun getAnalytics(@Header("Authorization") token: String): Map<String, Any>
    
    @GET("admin/users")
    suspend fun getAllUsers(@Header("Authorization") token: String): List<User>
} 