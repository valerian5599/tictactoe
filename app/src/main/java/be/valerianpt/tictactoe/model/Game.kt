package be.valerianpt.tictactoe.model

class Game {
    companion object {
        const val EMPTY = 0
        const val PLAYER = 1
        const val COMPUTER = 2
    }

    private var gridSize = 3 //peut être modifié
    private var grid = Array(gridSize) { IntArray(gridSize) { EMPTY } }

    fun makeMove(row: Int, col: Int): Boolean {
        if (grid[row][col] == EMPTY) {
            grid[row][col] = PLAYER
            return true
        }
        return false
    }

    fun playComputerMove() {
        val emptyCells = mutableListOf<Pair<Int, Int>>()
        for (i in 0 until gridSize) {
            for (j in 0 until gridSize) {
                if (grid[i][j] == EMPTY) {
                    emptyCells.add(Pair(i, j))
                }
            }
        }

        if (emptyCells.isNotEmpty()) {
            val (row, col) = emptyCells.random()
            grid[row][col] = COMPUTER
        }
    }


    fun resetGrid() {
        for (i in 0 until gridSize) {
            for (j in 0 until gridSize) {
                grid[i][j] = EMPTY
            }
        }
    }

    fun getGrid(): Array<IntArray> {
        return grid
    }

    fun getGridSize(): Int {
        return gridSize
    }

    fun checkGameEnd(): Int {
        // -1 : partie tjs en cours
        // 1 : joueur gagne
        // 2 : ordinateur

        // lignes
        for (i in 0 until gridSize) {
            if ((0 until gridSize).all { j -> grid[i][j] == grid[i][0] && grid[i][0] != EMPTY }) {
                return grid[i][0]
            }
        }

        // colonnes
        for (i in 0 until gridSize) {
            if ((0 until gridSize).all { j -> grid[j][i] == grid[0][i] && grid[0][i] != EMPTY }) {
                return grid[0][i]
            }
        }

        // 1ere diagonale
        if ((0 until gridSize).all { i -> grid[i][i] == grid[0][0] && grid[0][0] != EMPTY }) {
            return grid[0][0]
        }

        // 2eme diagonale
        if ((0 until gridSize).all { i -> grid[i][gridSize - i - 1] == grid[0][gridSize - 1] && grid[0][gridSize - 1] != EMPTY }) {
            return grid[0][gridSize - 1]
        }

        // égalité
        if (grid.all { row -> row.all { it != EMPTY } }) {
            return 0
        }

        return -1
    }
}