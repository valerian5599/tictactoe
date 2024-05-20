package be.valerianpt.tictactoe.model

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Grid {
    companion object {
        const val EMPTY = 0
        const val PLAYER = 1
        const val COMPUTER = 2
    }

    private val gridSize = 3
    private val grid = Array(gridSize) { IntArray(gridSize) { EMPTY } }

    fun playUserMove(row: Int, col: Int): Boolean {
        return if (grid[row][col] == EMPTY) {
            grid[row][col] = PLAYER
            true
        } else {
            false
        }
    }

    fun getGrid(): Array<IntArray> {
        return grid
    }


}