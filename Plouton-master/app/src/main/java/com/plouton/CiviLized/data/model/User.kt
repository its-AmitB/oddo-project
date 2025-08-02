package com.plouton.CiviLized.data.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("_id")
    val id: String,
    val username: String,
    val email: String,
    val role: String,
    val isBanned: Boolean = false,
    val createdAt: String? = null,
    val updatedAt: String? = null
) 