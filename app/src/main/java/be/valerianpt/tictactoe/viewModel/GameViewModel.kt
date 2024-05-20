package be.valerianpt.tictactoe.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GameViewModel: ViewModel() {
    companion object {
        const val EMPTY = 0
        const val PLAYER = 1
        const val COMPUTER = 2
    }

    private val _gameState = MutableLiveData<Array<IntArray>>()
    val gameState: LiveData<Array<IntArray>> = _gameState

    private var gridSize: Int = 5

    init {
        _gameState.value = Array(gridSize) { IntArray(gridSize) { EMPTY } }
    }

    fun cellClicked(row: Int, col: Int) {
        val currentState = _gameState.value ?: return

        if (currentState[row][col] == EMPTY) {
            currentState[row][col] = PLAYER

            _gameState.value = currentState
            /*if (!checkGameEnd()) {
                GlobalScope.launch {
                    delay(1000)
                    withContext(Dispatchers.Main) {
                        playComputerMove()
                    }
                }
            }
        }
    }*/

            /* private fun playComputerMove() {
        val currentState = _gameState.value ?: return

        val emptyCells = mutableListOf<Pair<Int, Int>>()
        for (i in 0 until gridSize) {
            for (j in 0 until gridSize) {
                if (currentState[i][j] == EMPTY) {
                    emptyCells.add(Pair(i, j))
                }
            }
        }

        if (emptyCells.isNotEmpty()) {
            val (row, col) = emptyCells.random()
            currentState[row][col] = COMPUTER
            _gameState = currentState
        }
    }*/

        }
    }
    }
