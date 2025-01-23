package com.example.tictactoeproject.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tictactoeproject.model.Game
import com.example.tictactoeproject.model.Player
import com.example.tictactoeproject.model.Cell

class GameViewModel : ViewModel() {

    private val game = Game()

    private val _currentPlayer = MutableLiveData<Player>()
    val currentPlayer: LiveData<Player> get() = _currentPlayer

    private val _winner = MutableLiveData<Player?>()
    val winner: LiveData<Player?> get() = _winner

    private val _isDraw = MutableLiveData<Boolean>()
    val isDraw: LiveData<Boolean> get() = _isDraw

    init {
        _currentPlayer.value = game.currentPlayer
    }

    fun makeMove(row: Int, col: Int): Boolean {
        val moveMade = game.makeMove(row, col)
        if (moveMade) {
            _currentPlayer.value = game.currentPlayer
            _winner.value = game.winner
            _isDraw.value = game.isDraw
        }
        return moveMade
    }

    fun getCell(row: Int, col: Int): Cell? {
        return game.getBoard()[row][col]
    }

    fun resetGame() {
        game.resetGame()
        _currentPlayer.value = game.currentPlayer
        _winner.value = null
        _isDraw.value = false
    }
}