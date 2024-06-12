package com.example.deltatask01

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun  GameBoard(
    viewModel : GameViewModel
){
    val gameState by viewModel.gameState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        gameState.board.forEach { row ->
            Row {
                row.forEach { tile ->
                    TileView(tile = tile, onClick = { viewModel.onTileClicked(tile) })
                }
            }
        }
        if (gameState.gameOver) {
            Text(text = "Game Over! ${gameState.currentPlayer} Wins!", fontSize = 24.sp, color = Color.Black)
        } else {
            Text(text = "Current Player: ${gameState.currentPlayer}", fontSize = 24.sp, color = Color.Black)
        }
    }
}


@Composable
fun TileView(tile: Tile, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .size(48.dp)
            .padding(2.dp)
            .background(
                when (tile.color) {
                    PlayerColor.RED -> Color.Red
                    PlayerColor.BLUE -> Color.Blue
                    else -> Color.Gray
                }, shape = RoundedCornerShape(4.dp)
            )
            .clickable { onClick() },
        elevation = 4.dp
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(text = tile.points.toString(), fontSize = 18.sp, color = Color.White)
        }
    }
}
