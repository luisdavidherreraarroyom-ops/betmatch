package com.example.betmatch.features.matches

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.betmatch.data.database.AppDatabase
import com.example.betmatch.data.database.MatchEntity
import com.example.betmatch.data.repository.MatchRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MatchViewModel(private val repository: MatchRepository) : ViewModel() {

    val matches: StateFlow<List<MatchEntity>> = repository.allMatches
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init {
        viewModelScope.launch {
            repository.refreshMatches()
        }
    }

    fun addMatch(
        participantA: String,
        participantB: String,
        durationInSeconds: Long,
        tournamentId: String? = null
    ) {
        viewModelScope.launch {
            repository.addMatch(
                MatchEntity(
                    tournamentId = tournamentId,
                    participantA = participantA,
                    participantB = participantB,
                    durationInSeconds = durationInSeconds
                )
            )
        }
    }

    fun updateMatch(match: MatchEntity, participantA: String, participantB: String, durationInSeconds: Long) {
        viewModelScope.launch {
            repository.updateMatch(
                match.copy(
                    participantA = participantA,
                    participantB = participantB,
                    durationInSeconds = durationInSeconds
                )
            )
        }
    }

    fun deleteMatch(match: MatchEntity) {
        viewModelScope.launch {
            repository.deleteMatch(match)
        }
    }

    companion object {
        fun provideFactory(context: android.content.Context): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras
                ): T {
                    val dao = AppDatabase.getInstance(context).matchDao()
                    val repository = MatchRepository(dao)
                    @Suppress("UNCHECKED_CAST")
                    return MatchViewModel(repository) as T
                }
            }
    }
}
