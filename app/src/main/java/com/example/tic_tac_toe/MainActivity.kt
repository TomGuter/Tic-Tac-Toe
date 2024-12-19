package com.example.tic_tac_toe

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var turn: TextView
    private var currentPlayer = "X"
    private lateinit var board: Array<String>
    private lateinit var buttons: Array<Button>
    private lateinit var playAgain: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        board = Array(9) { "" }
        buttons = arrayOf(
            findViewById(R.id.btn_00), findViewById(R.id.btn_01), findViewById(R.id.btn_02),
            findViewById(R.id.btn_10), findViewById(R.id.btn_11), findViewById(R.id.btn_12),
            findViewById(R.id.btn_20), findViewById(R.id.btn_21), findViewById(R.id.btn_22)
        )
        startGame()
    }



    private fun startGame() {
        turn = findViewById(R.id.PlayerTurn)
        playAgain = findViewById(R.id.playAgainBtn)
        currentPlayer = "X"
        turn.text = "Player $currentPlayer's turn"

        for (i in board.indices) {
            board[i] = ""
            buttons[i].text = ""
        }

        for (i in buttons.indices) {
            buttons[i].setOnClickListener {
                if (buttons[i].text != "" && board[i] != "") {
                    return@setOnClickListener
                }
                board[i] = currentPlayer
                buttons[i].text = currentPlayer
                if (currentPlayer == "X") buttons[i].setTextColor(resources.getColor(R.color.red))
                else buttons[i].setTextColor(resources.getColor(R.color.blue))

                if (checkWinner()) {
                    turn.text = "Player $currentPlayer wins!"
                    for (button in buttons) {
                        button.isEnabled = false
                    }
                    playAgain.visibility = Button.VISIBLE
                    playAgain.setOnClickListener() {
                        startGame()
                        playAgain.visibility = Button.INVISIBLE
                        for (button in buttons) {
                            button.isEnabled = true
                        }
                    }

                } else if (board.all { it != "" }) {
                    turn.text = "It's a draw!"
                    playAgain.visibility = Button.VISIBLE
                    playAgain.setOnClickListener() {
                        startGame()
                        playAgain.visibility = Button.INVISIBLE
                        for (button in buttons) {
                            button.isEnabled = true
                        }
                    }

                } else {
                    currentPlayer = if (currentPlayer == "X") "O" else "X"
                    turn.text = "Player $currentPlayer's turn"
                }
            }
        }
    }

    private fun checkWinner() : Boolean {
        var options_to_win = arrayOf(
            arrayOf(0, 1, 2), arrayOf(3, 4, 5), arrayOf(6, 7, 8),
            arrayOf(0, 3, 6), arrayOf(1, 4, 7), arrayOf(2, 5, 8),
            arrayOf(0, 4, 8), arrayOf(2, 4, 6)
        )

        for (option in options_to_win) {
            if (board[option[0]] == currentPlayer && board[option[1]] == currentPlayer && board[option[2]] == currentPlayer
                && board[option[0]] != "") {
                return true
            }
        }

        return false


    }



}