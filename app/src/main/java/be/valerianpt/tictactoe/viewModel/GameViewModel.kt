package be.valerianpt.tictactoe.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.valerianpt.tictactoe.model.Game
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class GameViewModel : ViewModel() {

    private val game = Game()

    private val _gameGrid = MutableLiveData<Array<IntArray>>()
    val gameGrid: LiveData<Array<IntArray>> get() = _gameGrid

    private val _winner = MutableLiveData<Int>()
    val winner: LiveData<Int> get() = _winner

    private val _currentPlayer = MutableLiveData<Int>()
    val currentPlayer: LiveData<Int> get() = _currentPlayer

    init {
        startGame()
    }

    private fun startGame() {
        updateGameGrid()
        chooseFirstPlayer()
        if (_currentPlayer.value == Game.COMPUTER) {
            playComputerMove()
        }
    }

    fun getGridSize(): Int {
        return game.getGridSize()
    }

    private fun updateGameGrid() {
        _gameGrid.value = game.getGrid()
    }

    private fun playComputerMove() {
        viewModelScope.launch {
            delay(1000)
            game.playComputerMove()
            updateGameGrid()
            _winner.value = game.checkGameEnd()
            _currentPlayer.value = Game.PLAYER
        }
    }

    private fun chooseFirstPlayer() {
        // choisir aléatoirement quel joueur commencera
        _currentPlayer.value = if (Random.nextBoolean()) Game.PLAYER else Game.COMPUTER
    }


    fun makeMove(row: Int, col: Int) {
        if (_currentPlayer.value == Game.PLAYER) {
            if (game.makeMove(row, col)) {
                updateGameGrid()
                _currentPlayer.value = Game.COMPUTER
                if (game.checkGameEnd() == -1) {
                    playComputerMove()
                } else {
                    _winner.value = game.checkGameEnd()
                }
            }
        }
    }

    fun resetGrid() {
        game.resetGrid()
        updateGameGrid()
        chooseFirstPlayer()
        if (_currentPlayer.value == Game.COMPUTER) {
            playComputerMove()
        }
    }
}