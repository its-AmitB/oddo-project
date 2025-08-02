package com.plouton.CiviLized.data.model

import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("_id")
    val id: String,
    val name: String,
    val description: String,
    val icon: String,
    val color: String,
    val isActive: Boolean = true,
    val createdAt: String? = null,
    val updatedAt: String? = null
) 