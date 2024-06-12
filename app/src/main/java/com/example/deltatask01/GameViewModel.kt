package com.example.deltatask01

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GameViewModel : ViewModel() {
    private val _gameState = MutableStateFlow(GameState())
    val gameState: StateFlow<GameState> = _gameState

    fun onTileClicked(tile: Tile) {
        if (_gameState.value.gameOver) return

        val board = _gameState.value.board.map { row -> row.map { it.copy() } }.toMutableList()
        val currentTile = board[tile.x][tile.y]

        if (currentTile.color == null && tileAvailableForFirstTurn()) {
            currentTile.color = _gameState.value.currentPlayer
            currentTile.points = 3
        } else if (currentTile.color == _gameState.value.currentPlayer) {
            currentTile.points++
            if (currentTile.points == 4) {
                triggerExpansion(currentTile, board)
            }
        } else {
            return
        }

        checkGameOver(board)
        switchPlayer()
        _gameState.value = GameState(board = board, currentPlayer = _gameState.value.currentPlayer, gameOver = _gameState.value.gameOver)
    }

    private fun triggerExpansion(tile: Tile, board: MutableList<List<Tile>>) {
        val directions = listOf(Pair(0, 1), Pair(1, 0), Pair(0, -1), Pair(-1, 0))
        tile.color = null
        tile.points = 0

        directions.forEach { (dx, dy) ->
            val nx = tile.x + dx
            val ny = tile.y + dy
            if (nx in board.indices && ny in board[0].indices) {
                val neighbor = board[nx][ny]
                if (neighbor.color != _gameState.value.currentPlayer) {
                    neighbor.color = _gameState.value.currentPlayer
                    neighbor.points = 1
                } else {
                    neighbor.points++
                }
                if (neighbor.points == 4) {
                    triggerExpansion(neighbor, board)
                }
            }
        }
    }

    private fun switchPlayer() {
        _gameState.value = _gameState.value.copy(currentPlayer = if (_gameState.value.currentPlayer == PlayerColor.RED) PlayerColor.BLUE else PlayerColor.RED)
    }

    private fun checkGameOver(board: List<List<Tile>>) {
        val opponentColor = if (_gameState.value.currentPlayer == PlayerColor.RED) PlayerColor.BLUE else PlayerColor.RED
        if (board.flatten().none { it.color == opponentColor }) {
            _gameState.value = _gameState.value.copy(gameOver = true)
        }
    }

    private fun tileAvailableForFirstTurn(): Boolean {
        val tiles = _gameState.value.board.flatten()
        return tiles.count { it.color == PlayerColor.RED } < 1 && tiles.count { it.color == PlayerColor.BLUE } < 1
    }
}
