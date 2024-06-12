package com.example.deltatask01

data class Tile(
val x: Int,
val y: Int,
var color: PlayerColor? = null,
var points: Int = 0
)

enum class PlayerColor {
    RED, BLUE
}

data class GameState(
    val board: List<List<Tile>> = List(10) { x -> List(10) { y -> Tile(x, y) } },
    val currentPlayer: PlayerColor = PlayerColor.RED,
    val gameOver: Boolean = false
)

