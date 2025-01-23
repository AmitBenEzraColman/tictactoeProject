package com.example.tictactoeproject.model

class Game {

    private val board: Array<Array<Cell?>> = Array(3) { Array(3) { null } }

    var currentPlayer: Player = Player.X
    var winner: Player? = null
    var isDraw: Boolean = false


    fun getBoard(): Array<Array<Cell?>> = board


    fun makeMove(row: Int, col: Int): Boolean {

        if (board[row][col] == null && winner == null && !isDraw) {
            board[row][col] = Cell(row, col, currentPlayer)
            checkGameState()
            switchPlayer()
            return true
        }
        return false
    }

    private fun switchPlayer() {
        currentPlayer = if (currentPlayer == Player.X) Player.O else Player.X
    }

    private fun checkGameState() {
        for (i in 0..2) {
            if (board[i][0]?.player == board[i][1]?.player && board[i][1]?.player == board[i][2]?.player && board[i][0]?.player != null) {
                winner = board[i][0]?.player
                return
            }
            if (board[0][i]?.player == board[1][i]?.player && board[1][i]?.player == board[2][i]?.player && board[0][i]?.player != null) {
                winner = board[0][i]?.player
                return
            }
        }

        if (board[0][0]?.player == board[1][1]?.player && board[1][1]?.player == board[2][2]?.player && board[0][0]?.player != null) {
            winner = board[0][0]?.player
        }
        if (board[0][2]?.player == board[1][1]?.player && board[1][1]?.player == board[2][0]?.player && board[0][2]?.player != null) {
            winner = board[0][2]?.player
        }


        if (board.all { row -> row.all { it != null } }) {
            isDraw = true
        }
    }

    fun resetGame() {
        for (row in 0..2) {
            for (col in 0..2) {
                board[row][col] = null
            }
        }
        currentPlayer = Player.X
        winner = null
        isDraw = false
    }
}
