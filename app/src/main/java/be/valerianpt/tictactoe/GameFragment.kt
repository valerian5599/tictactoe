package be.valerianpt.tictactoe

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.ImageView
import androidx.core.view.marginTop
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import be.valerianpt.tictactoe.databinding.FragmentGameBinding
import be.valerianpt.tictactoe.model.Computer
import be.valerianpt.tictactoe.model.Player
import be.valerianpt.tictactoe.viewModel.GameViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class GameFragment : Fragment(), ResultFragmentDialog.ResultFragmentListener {
    private val viewModel: GameViewModel by viewModels()
    //CONST
    val EMPTY = 0
    val PLAYER = 1
    val COMPUTER = 2
    private var gridSize: Int = 3
    private val gameArrays = Array(gridSize) { IntArray(gridSize) { EMPTY } }
    private var isPlayerTurn = true
    var currentPlayer = Random.nextInt(1,3)

    var resultFragment = ResultFragmentDialog()


    private lateinit var binding: FragmentGameBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGameBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupGameGrid(gridSize)

        viewModel.gameState.observe(viewLifecycleOwner) { gameState ->
            //TODO:
        }


        binding.relaunchGameButton.setOnClickListener {
            resetGame(gridSize)
        }

        binding.homeButton.setOnClickListener {
            findNavController().popBackStack(R.id.homeFragment, false)
        }
    }

    //Setup
    private fun setupGameGrid(size: Int) {
        val gridLayout = binding.gameGrid
        gridLayout.columnCount = size

        for (row in 0 until size) {
            for (col in 0 until size) {
                val button = setupGridButton(row, col)
                gridLayout.addView(button)
            }
        }
    }

    private fun setupGridButton(row: Int, col: Int): ImageButton {
        val button = ImageButton(requireContext())
        val buttonId = "$row$col"
        button.id = buttonId.toInt()
        button.scaleType = ImageView.ScaleType.FIT_CENTER
        button.setBackgroundResource(R.drawable.grid_button_background)
        button.setOnClickListener {
            if (gameArrays[row][col] == EMPTY) {
                gameArrays[row][col] = PLAYER
                updateGridUI()
                if (!checkGameEnd()) {
                    GlobalScope.launch {
                        delay(1000)
                        withContext(Dispatchers.Main) {
                            playComputerMove()
                        }
                    }
                }
            }
        }

        val params = GridLayout.LayoutParams()
        params.width = 200
        params.height = 200
        params.bottomMargin = 10
        params.topMargin = 10
        params.marginStart = 10
        params.marginEnd = 10
        button.layoutParams = params
        return button
    }

    private fun resetGame(size: Int) {
        for (i in 0 until size) {
            for (j in 0 until size) {
                gameArrays[i][j] = EMPTY
                val buttonId = "$i$j"
                view?.findViewById<ImageButton>(buttonId.toInt())?.setImageResource(0)
            }
        }
        isPlayerTurn = true
        binding.gameTitle.text = "C'est votre tour"
    }

    private fun showEndGameDialog(gameResult: Int) {
        val resultFragment = ResultFragmentDialog.newInstance(gameResult)
        resultFragment.setOnButtonClickListener(this)
        resultFragment.show(childFragmentManager, ResultFragmentDialog.TAG)
    }

    private fun checkGameEnd(): Boolean {
        // lignes
        for (i in 0 until gridSize) {
            if ((0 until gridSize).all { j -> gameArrays[i][j] == gameArrays[i][0] && gameArrays[i][0] != EMPTY }) {
                showEndGameDialog(gameArrays[i][0])
                return true
            }
        }

        // colonnes
        for (i in 0 until gridSize) {
            if ((0 until gridSize).all { j -> gameArrays[j][i] == gameArrays[0][i] && gameArrays[0][i] != EMPTY }) {
                showEndGameDialog(gameArrays[0][i])
                return true
            }
        }

        // première diagonale
        if ((0 until gridSize).all { i -> gameArrays[i][i] == gameArrays[0][0] && gameArrays[0][0] != EMPTY }) {
            showEndGameDialog(gameArrays[0][0])
            return true
        }

        // deuxième diagonale
        if ((0 until gridSize).all { i -> gameArrays[i][gridSize - i - 1] == gameArrays[0][gridSize - 1] && gameArrays[0][gridSize - 1] != EMPTY }) {
            showEndGameDialog(gameArrays[0][gridSize - 1])
            return true
        }

        // égalité
        if (gameArrays.all { row -> row.all { it != EMPTY } }) {
            showEndGameDialog(0)
            return true
        }
        return false
    }

    private fun playComputerMove() {
        val emptyCells = mutableListOf<Pair<Int, Int>>()
        for (i in 0 until gridSize) {
            for (j in 0 until gridSize) {
                if (gameArrays[i][j] == EMPTY) {
                    emptyCells.add(Pair(i, j))
                }
            }
        }

        if (emptyCells.isNotEmpty()) {
            val (row, col) = emptyCells.random()
            gameArrays[row][col] = COMPUTER

            updateGridUI()
            binding.gameTitle.text = "Votre tour"
            isPlayerTurn = true
            checkGameEnd()
        }
    }

    private fun updateGridUI() {
        for (i in 0 until gridSize) {
            for (j in 0 until gridSize) {
                val buttonId = "$i$j"
                val button = view?.findViewById<ImageButton>(buttonId.toInt())
                when (gameArrays[i][j]) {
                    1 -> button?.setImageResource(Player.getImageResource())
                    2 -> button?.setImageResource(Computer.getImageResource())
                    else -> button?.setImageResource(0)
                }
            }
        }
    }

    override fun onRestartGame() {
        resetGame(gridSize)
    }

    override fun onReturnToHome() {
        findNavController().popBackStack(R.id.homeFragment, false)
    }
}