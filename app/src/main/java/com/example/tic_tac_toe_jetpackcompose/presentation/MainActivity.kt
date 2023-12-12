package com.example.tic_tac_toe_jetpackcompose.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tic_tac_toe_jetpackcompose.data.model.Player
import com.example.tic_tac_toe_jetpackcompose.ui.theme.myBackgroundColor
import com.example.tic_tac_toe_jetpackcompose.ui.theme.myBoxColor
import com.example.tic_tac_toe_jetpackcompose.ui.theme.playerOneColor
import com.example.tic_tac_toe_jetpackcompose.ui.theme.playerTwoColor
import dagger.hilt.android.AndroidEntryPoint
import androidx.hilt.navigation.compose.hiltViewModel


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TicTacToeGame()
        }
    }
}

@Composable
fun TicTacToeGame(
    viewModel: TicTacToeViewModel = hiltViewModel()
) {
    val board by viewModel.board.collectAsState()
    val currentPlayer by viewModel.currentPlayer.collectAsState()
    val winner by viewModel.winner.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = myBackgroundColor,
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = if (winner == null) "Tic Tac Toe" else "Winner: ${winner!!.name}",
                style = MaterialTheme.typography.displayLarge,
                color = when (winner) {
                    Player.X -> playerOneColor
                    Player.O -> playerTwoColor
                    else -> Color.White
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            TicTacToeBoard(
                board = board,
                onCellClick = { row, col ->
                    viewModel.onCellClick(row, col)
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    viewModel.resetGame()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = myBoxColor
                )
            ) {
                Text("Restart Game")
            }
        }
    }
}

@Composable
fun TicTacToeBoard(board: Array<Array<Player>>, onCellClick: (Int, Int) -> Unit) {
    LazyColumn {
        items(board.size) { row ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                for (col in board[row].indices) {
                    TicTacToeCell(
                        row = row,
                        col = col,
                        player = board[row][col],
                        onCellClick = { clickedRow, clickedCol ->
                            onCellClick(clickedRow, clickedCol)
                        }
                    )

                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    }
}

@Composable
fun TicTacToeCell(row: Int, col: Int, player: Player, onCellClick: (Int, Int) -> Unit) {
    Box(
        modifier = Modifier
            .size(50.dp)
            .background(myBoxColor)
            .clickable {
                onCellClick(row, col)
            }
            .padding(4.dp)
            .clip(RectangleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = when (player) {
                Player.X -> "X"
                Player.O -> "O"
                else -> ""
            },
            color = when (player) {
                Player.X -> playerOneColor
                Player.O -> playerTwoColor
                else -> Color.White
            },
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TicTacToeGamePreview() {
    TicTacToeGame()
}