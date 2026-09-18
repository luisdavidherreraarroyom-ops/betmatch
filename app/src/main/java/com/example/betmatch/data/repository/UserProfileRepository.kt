package com.example.betmatch.data.repository

import com.example.betmatch.data.database.UserProfileDao
import com.example.betmatch.data.database.UserProfileEntity
import kotlinx.coroutines.flow.Flow

class UserProfileRepository(private val userProfileDao: UserProfileDao) {

    val profile: Flow<UserProfileEntity?> = userProfileDao.getProfile()

    suspend fun saveProfile(profile: UserProfileEntity) {
        userProfileDao.saveProfile(profile)
    }
}