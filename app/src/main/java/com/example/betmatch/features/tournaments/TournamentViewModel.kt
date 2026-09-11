package com.example.betmatch.features.tournaments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.betmatch.data.database.AppDatabase
import com.example.betmatch.data.database.TournamentEntity
import com.example.betmatch.data.repository.TournamentRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TournamentViewModel(private val repository: TournamentRepository) : ViewModel() {

    val tournaments: StateFlow<List<TournamentEntity>> = repository.allTournaments
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addTournament(title: String, rules: String, maxPlayers: Int) {
        viewModelScope.launch {
            repository.addTournament(
                TournamentEntity(
                    title = title,
                    rules = rules,
                    maxPlayers = maxPlayers
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
                    val dao = AppDatabase.getInstance(context).tournamentDao()
                    val repository = TournamentRepository(dao)
                    @Suppress("UNCHECKED_CAST")
                    return TournamentViewModel(repository) as T
                }
            }
    }
}