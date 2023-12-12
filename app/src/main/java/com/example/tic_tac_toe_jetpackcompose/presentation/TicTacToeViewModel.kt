package com.example.tic_tac_toe_jetpackcompose.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tic_tac_toe_jetpackcompose.data.model.Player
import com.example.tic_tac_toe_jetpackcompose.data.reposatory.TicTacToeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TicTacToeViewModel @Inject constructor(private val repository: TicTacToeRepository) : ViewModel() {

    val board: StateFlow<Array<Array<Player>>> = repository.board
    val currentPlayer: StateFlow<Player> = repository.currentPlayer
    val winner: StateFlow<Player?> = repository.winner

    fun onCellClick(row: Int, col: Int) {
        viewModelScope.launch {
            repository.onCellClick(row, col)
        }
    }

    fun resetGame() {
        viewModelScope.launch {
            repository.resetGame()
        }
    }
}