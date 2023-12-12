package com.example.tic_tac_toe_jetpackcompose.data.reposatory

import com.example.tic_tac_toe_jetpackcompose.data.model.Player
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class TicTacToeRepository @Inject constructor(){

    private val _board = MutableStateFlow(Array(3) { Array(3) { Player.Empty } })
    val board: StateFlow<Array<Array<Player>>> = _board

    private val _currentPlayer = MutableStateFlow(Player.X)
    val currentPlayer: StateFlow<Player> = _currentPlayer

    private val _winner = MutableStateFlow<Player?>(null)
    val winner: StateFlow<Player?> = _winner

    init {
        resetGame()
    }

    fun resetGame() {
        _board.value = Array(3) { Array(3) { Player.Empty } }
        _currentPlayer.value = Player.X
        _winner.value = null
    }

    fun onCellClick(row: Int, col: Int) {
        val currentPlayer = _currentPlayer.value
        if (_board.value[row][col] == Player.Empty && _winner.value == null) {
            _board.value = _board.value.copyOf()
            _board.value[row] = _board.value[row].copyOf()
            _board.value[row][col] = currentPlayer

            if (checkForWinner(_board.value, currentPlayer)) {
                _winner.value = currentPlayer
            } else if (isBoardFull(_board.value)) {
                _winner.value = Player.Empty
            } else {
                _currentPlayer.value = if (currentPlayer == Player.X) Player.O else Player.X
            }
        }
    }

    private fun isBoardFull(board: Array<Array<Player>>): Boolean {
        return board.flatten().all { it != Player.Empty }
    }

    private fun checkForWinner(board: Array<Array<Player>>, currentPlayer: Player): Boolean {
        // Check rows and columns
        for (i in board.indices) {
            if ((board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) ||
                (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer)
            ) {
                return true
            }
        }

        // Check diagonals
        if ((board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) ||
            (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer)
        ) {
            return true
        }

        return false
    }
}