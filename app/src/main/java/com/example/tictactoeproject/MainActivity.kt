package com.example.tictactoeproject

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.tictactoeproject.viewModel.GameViewModel

class MainActivity : AppCompatActivity() {

    private val gameViewModel: GameViewModel by viewModels()

    private lateinit var currentPlayerTextView: TextView
    private lateinit var playAgainButton: Button
    private lateinit var gridLayout: GridLayout

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        currentPlayerTextView = findViewById(R.id.currentPlayerTextView)
        playAgainButton = findViewById(R.id.playAgainButton)
        gridLayout = findViewById(R.id.gridLayout)


        setUpBoard()


        gameViewModel.currentPlayer.observe(this, Observer { player ->
            currentPlayerTextView.text = "Current Player: $player"
        })

        gameViewModel.winner.observe(this, Observer { winner ->
            if (winner != null) {
                currentPlayerTextView.text = "$winner Wins!"
                playAgainButton.visibility = View.VISIBLE
            } else if (gameViewModel.isDraw.value == true) {
                currentPlayerTextView.text = "It's a Draw!"
                playAgainButton.visibility = View.VISIBLE
            }
        })


        playAgainButton.setOnClickListener {
            gameViewModel.resetGame()
            playAgainButton.visibility = View.GONE
            updateBoard()
        }
    }

    private fun setUpBoard() {

        for (row in 0..2) {
            for (col in 0..2) {
                val buttonId = resources.getIdentifier("button$row$col", "id", packageName)
                val button: Button = findViewById(buttonId)
                button.setOnClickListener {
                    onCellClicked(row, col)
                }
            }
        }
    }

    private fun onCellClicked(row: Int, col: Int) {
        if (gameViewModel.makeMove(row, col)) {
            updateBoard()
        }
    }

    private fun updateBoard() {

        for (row in 0..2) {
            for (col in 0..2) {
                val buttonId = resources.getIdentifier("button$row$col", "id", packageName)
                val button: Button = findViewById(buttonId)
                val cell = gameViewModel.getCell(row, col)
                button.text = cell?.player?.name
                button.isEnabled = cell?.player == null // Disable button if it's already filled
            }
        }
    }
}
