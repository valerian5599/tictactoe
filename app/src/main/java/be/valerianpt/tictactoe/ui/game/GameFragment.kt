package be.valerianpt.tictactoe.ui.game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import be.valerianpt.tictactoe.R
import be.valerianpt.tictactoe.databinding.FragmentGameBinding
import be.valerianpt.tictactoe.model.Computer
import be.valerianpt.tictactoe.model.Player
import be.valerianpt.tictactoe.viewModel.GameViewModel

class GameFragment : Fragment(), ResultFragmentDialog.ResultFragmentListener {

    private lateinit var binding: FragmentGameBinding
    private val viewModel: GameViewModel by viewModels()
    private var gridSize: Int = 0

    // Views lifecycle
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameBinding.inflate(layoutInflater)
        gridSize = viewModel.getGridSize()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupGameGrid(gridSize)
        setupButton()
        setupObserver()
    }

    // Setup
    private fun setupObserver() {
        // je mets à jour l'UI de la grille en fonction du tableau se trouvant dans Game()
        viewModel.gameGrid.observe(viewLifecycleOwner) { gameState ->
            updateGridUI(gameState)
        }

        viewModel.currentPlayer.observe(viewLifecycleOwner) { currentPlayer ->
            updateTitle(currentPlayer)
        }

        viewModel.winner.observe(viewLifecycleOwner) { winner ->
            when (winner) {
                1 -> showEndGameDialog(winner)
                2 -> showEndGameDialog(winner)
                0 -> showEndGameDialog(winner)
                else -> {}
            }
        }
    }

    private fun setupButton() {
        binding.relaunchGameButton.setOnClickListener {
            resetGame()
        }

        binding.backHomeButton.setOnClickListener {
            findNavController().popBackStack(R.id.homeFragment, false)
        }
    }

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
        val params = GridLayout.LayoutParams()
        params.width = 200
        params.height = 200
        params.bottomMargin = 10
        params.topMargin = 10
        params.marginStart = 10
        params.marginEnd = 10
        button.layoutParams = params
        button.setOnClickListener {
            viewModel.makeMove(row, col)
        }
        return button
    }

    private fun updateTitle(currentPlayer: Int) {
        binding.gameTitle.text = when (currentPlayer) {
            1 -> getString(R.string.game_title_player_turn)
            else -> getString(R.string.game_title_computer_turn)
        }
    }

    private fun updateGridUI(grid: Array<IntArray>) {
        for (i in 0 until gridSize) {
            for (j in 0 until gridSize) {
                val buttonId = "$i$j"
                val button = view?.findViewById<ImageButton>(buttonId.toInt())
                when (grid[i][j]) {
                    1 -> button?.setImageResource(Player.getImageResource())
                    2 -> button?.setImageResource(Computer.getImageResource())
                    else -> button?.setImageResource(0)
                }
            }
        }
    }

    private fun resetGame() {
        viewModel.resetGrid()
    }

    private fun showEndGameDialog(gameResult: Int) {
        val resultFragment = ResultFragmentDialog.newInstance(gameResult)
        resultFragment.setOnButtonClickListener(this)
        resultFragment.show(childFragmentManager, ResultFragmentDialog.TAG)
    }

    override fun onRestartGame() {
        resetGame()
    }

    override fun onReturnToHome() {
        findNavController().popBackStack(R.id.homeFragment, false)
    }
}