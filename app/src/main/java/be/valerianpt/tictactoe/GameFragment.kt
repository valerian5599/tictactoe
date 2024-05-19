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
import androidx.navigation.fragment.findNavController
import be.valerianpt.tictactoe.databinding.FragmentGameBinding
import be.valerianpt.tictactoe.model.Computer
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GameFragment : Fragment() {
    //private val viewModel: GameViewModel by viewModels()
    //CONST
    val EMPTY = 0
    val PLAYER = 1
    val COMPUTER = 2
    private var gridSize: Int = 4
    private val gameArrays = Array(gridSize) { IntArray(gridSize) { EMPTY } }
    private var isPlayerTurn = true

    var resultFragment = ResultFragment()


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
                val button  = setupGridButton(row,col)
                gridLayout.addView(button)
            }
        }
    }

    private fun setupGridButton(row: Int, col: Int): ImageButton {
        val button = ImageButton(requireContext())
        val buttonId = "$row$col"
        button.id = buttonId.toInt()
        button.scaleType = ImageView.ScaleType.FIT_CENTER
        button.setOnClickListener {
            //TODO: handle button click
            //handleButtonClick(i, j)
        }
        val params = GridLayout.LayoutParams()
        params.width = 200
        params.height = 200
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

    private fun checkGameEnd(): Boolean {
        // lignes
        for (i in 0 until gridSize) {
            if ((0 until gridSize).all { j -> gameArrays[i][j] == gameArrays[i][0] && gameArrays[i][0] != EMPTY }) {
                showEndGameDialog(if (gameArrays[i][0] == PLAYER) "Vous avez gagné!" else "Vous avez perdu..")
                return true
            }
        }

        // colonnes
        for (i in 0 until gridSize) {
            if ((0 until gridSize).all { j -> gameArrays[j][i] == gameArrays[0][i] && gameArrays[0][i] != EMPTY }) {
                resultFragment.show(requireActivity().supportFragmentManager, "resultFragment")
                showEndGameDialog(if (gameArrays[0][i] == PLAYER) "Vous avez gagné!" else "Vous avez perdu..")
                return true
            }
        }

        // première diagonale
        if ((0 until gridSize).all { i -> gameArrays[i][i] == gameArrays[0][0] && gameArrays[0][0] != EMPTY }) {
            showEndGameDialog(if (gameArrays[0][0] == PLAYER) "Vous avez gagné!" else "Vous avez perdu..")
            return true
        }

        // deuxième diagonale
        if ((0 until gridSize).all { i -> gameArrays[i][gridSize - i - 1] == gameArrays[0][gridSize - 1] && gameArrays[0][gridSize - 1] != EMPTY }) {
            showEndGameDialog(if (gameArrays[0][gridSize - 1] == PLAYER) "Vous avez gagné!" else "Vous avez perdu..")
            return true
        }

        // égalité
        if (gameArrays.all { row -> row.all { it != EMPTY } }) {
            showEndGameDialog("Match nul")
            return true
        }
        return false
    }

    private fun showEndGameDialog(message: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Fin de la partie")
            .setMessage(message)
            .show()
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

            val buttonId = "$row$col"
            view?.findViewById<ImageButton>(buttonId.toInt())
                ?.setImageResource(Computer.getImageResource())
            binding.gameTitle.text = "Votre tour"
            isPlayerTurn = true
            checkGameEnd()
        }
    }

    private fun showAlertDialog(message: String) {
        val builder = AlertDialog.Builder(context)
            .setTitle("Erreur")
            .setMessage(message)
            .setPositiveButton("Ok") { dialog, which ->
            }
        builder.show()
    }
}