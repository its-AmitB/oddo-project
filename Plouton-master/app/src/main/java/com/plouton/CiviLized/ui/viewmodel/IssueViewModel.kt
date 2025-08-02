package com.plouton.CiviLized.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plouton.CiviLized.data.model.Issue
import com.plouton.CiviLized.data.model.PaginatedResponse
import com.plouton.CiviLized.data.repository.IssueRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IssueViewModel @Inject constructor(
    private val issueRepository: IssueRepository
) : ViewModel() {
    
    private val _issues = MutableStateFlow<List<Issue>>(emptyList())
    val issues: StateFlow<List<Issue>> = _issues.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()
    
    fun getNearbyIssues(lat: Double, lng: Double, radius: Int = 5000) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            issueRepository.getNearbyIssues(lat, lng, radius)
                .onSuccess { issues ->
                    _issues.value = issues
                }
                .onFailure { exception ->
                    _error.value = exception.message ?: "Unknown error occurred"
                }
            
            _isLoading.value = false
        }
    }
    
    fun getIssues(
        status: String? = null,
        category: String? = null,
        lat: Double? = null,
        lng: Double? = null,
        radius: Int = 5000,
        page: Int = 1,
        limit: Int = 20
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            issueRepository.getIssues(status, category, lat, lng, radius, page, limit)
                .onSuccess { response ->
                    _issues.value = response.issues
                }
                .onFailure { exception ->
                    _error.value = exception.message ?: "Unknown error occurred"
                }
            
            _isLoading.value = false
        }
    }
    
    fun createIssue(
        title: String,
        description: String,
        category: String,
        lat: Double,
        lng: Double,
        address: String = "",
        anonymous: Boolean = false,
        token: String? = null
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            issueRepository.createIssue(
                title = title,
                description = description,
                category = category,
                lat = lat,
                lng = lng,
                address = address,
                anonymous = anonymous,
                token = token
            )
                .onSuccess { issue ->
                    // Add the new issue to the list
                    _issues.value = _issues.value + issue
                }
                .onFailure { exception ->
                    _error.value = exception.message ?: "Failed to create issue"
                }
            
            _isLoading.value = false
        }
    }
    
    fun flagIssue(issueId: String, reason: String, token: String? = null) {
        viewModelScope.launch {
            issueRepository.flagIssue(issueId, reason, token)
                .onFailure { exception ->
                    _error.value = exception.message ?: "Failed to flag issue"
                }
        }
    }
    
    fun clearError() {
        _error.value = null
    }
} 