package com.example.betmatch.features.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.betmatch.data.database.AppDatabase
import com.example.betmatch.data.database.UserProfileEntity
import com.example.betmatch.data.repository.UserProfileRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: UserProfileRepository) : ViewModel() {

    val profile: StateFlow<UserProfileEntity?> = repository.profile
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    fun saveProfile(username: String, email: String, balance: Double) {
        viewModelScope.launch {
            repository.saveProfile(
                UserProfileEntity(
                    username = username,
                    email = email,
                    balance = balance
                )
            )
        }
    }

    companion object {
        fun provideFactory(context: android.content.Context): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras
                ): T {
                    val dao = AppDatabase.getInstance(context).userProfileDao()
                    val repository = UserProfileRepository(dao)
                    @Suppress("UNCHECKED_CAST")
                    return ProfileViewModel(repository) as T
                }
            }
    }
}