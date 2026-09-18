package com.example.betmatch.features.bets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.betmatch.data.database.AppDatabase
import com.example.betmatch.data.database.BetEntity
import com.example.betmatch.data.repository.BetRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class BetViewModel(private val repository: BetRepository) : ViewModel() {

    val bets: StateFlow<List<BetEntity>> = repository.allBets
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun placeBet(matchTitle: String, chosenOption: String, amount: Double) {
        viewModelScope.launch {
            repository.insertBet(
                BetEntity(
                    matchTitle = matchTitle,
                    chosenOption = chosenOption,
                    amount = amount
                )
            )
        }
    }

    fun updateBet(bet: BetEntity, matchTitle: String, chosenOption: String, amount: Double) {
        viewModelScope.launch {
            repository.updateBet(
                bet.copy(
                    matchTitle = matchTitle,
                    chosenOption = chosenOption,
                    amount = amount
                )
            )
        }
    }

    fun setBetStatus(bet: BetEntity, newStatus: String) {
        viewModelScope.launch {
            repository.updateBet(bet.copy(status = newStatus))
        }
    }

    fun deleteBet(bet: BetEntity) {
        viewModelScope.launch {
            repository.deleteBet(bet)
        }
    }

    companion object {
        fun provideFactory(context: android.content.Context): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras
                ): T {
                    val dao = AppDatabase.getInstance(context).betDao()
                    val repository = BetRepository(dao)
                    @Suppress("UNCHECKED_CAST")
                    return BetViewModel(repository) as T
                }
            }
    }
}