package be.valerianpt.tictactoe.viewModel

import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {
    companion object {
        const val EMPTY = 0
        const val PLAYER = 1
        const val COMPUTER = 2
    }

    private var gridSize: Int = 5
    private val gameArrays = Array(gridSize) { IntArray(gridSize) { EMPTY } }


}