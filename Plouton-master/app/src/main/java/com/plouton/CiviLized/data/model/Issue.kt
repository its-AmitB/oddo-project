package com.plouton.CiviLized.data.model

import com.google.gson.annotations.SerializedName

data class Issue(
    @SerializedName("_id")
    val id: String,
    val title: String,
    val description: String,
    val category: Category,
    val location: Location,
    val address: String = "",
    val images: List<String> = emptyList(),
    val status: String = "Reported",
    val statusLogs: List<StatusLog> = emptyList(),
    val reportedBy: User? = null,
    val anonymous: Boolean = false,
    val flags: List<Flag> = emptyList(),
    val isFlagged: Boolean = false,
    val upvotes: Int = 0,
    val downvotes: Int = 0,
    val resolvedAt: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
)

data class Location(
    val type: String = "Point",
    val coordinates: List<Double>
)

data class StatusLog(
    val status: String,
    val timestamp: String,
    val note: String = ""
)

data class Flag(
    val userId: String? = null,
    val reason: String,
    val timestamp: String
)

data class CreateIssueRequest(
    val title: String,
    val description: String,
    val category: String,
    val lat: Double,
    val lng: Double,
    val address: String = "",
    val anonymous: Boolean = false
)

data class UpdateStatusRequest(
    val status: String,
    val note: String = ""
)

data class FlagIssueRequest(
    val reason: String
)

data class AuthRequest(
    val email: String,
    val password: String
)

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String
)

data class AuthResponse(
    @SerializedName("_id")
    val id: String,
    val username: String,
    val email: String,
    val role: String,
    val token: String
)

data class ApiResponse<T>(
    val success: Boolean,
    val data: T? = null,
    val message: String? = null
)

data class PaginatedResponse<T>(
    val issues: List<T>,
    val pagination: Pagination
)

data class Pagination(
    val page: Int,
    val limit: Int,
    val total: Int,
    val pages: Int
) 