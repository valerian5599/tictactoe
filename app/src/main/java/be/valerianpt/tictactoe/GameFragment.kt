package be.valerianpt.tictactoe

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
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
import be.valerianpt.tictactoe.model.Player
import kotlinx.coroutines.launch

class GameFragment : Fragment() {
    //CONST
    val EMPTY = 0
    val PLAYER = 1
    val COMPUTER = 2

    val gameArrays = Array(3) { IntArray(3) { EMPTY } }

    private lateinit var binding: FragmentGameBinding
    private var gridSize: Int = 3
    private lateinit var gridLayout: GridLayout

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
            resetGame(3)
        }

        binding.homeButton.setOnClickListener {
            findNavController().popBackStack(R.id.homeFragment, false)
        }
    }

    private fun resetGame(size: Int) {
        for (i in 0 until size) {
            for (j in 0 until size) {
                gameArrays[i][j] = EMPTY
                val buttonId = "$i$j"
                view?.findViewById<ImageButton>(buttonId.toInt())?.setImageResource(0)
            }
        }
    }

    private fun checkGameEnd(): Boolean {
        // lignes
        for (i in 0..2) {
            if (gameArrays[i][0] == gameArrays[i][1] && gameArrays[i][1] == gameArrays[i][2] && gameArrays[i][0] != EMPTY) {
                showEndGameDialog(if (gameArrays[i][0] == PLAYER) "Vous avez gagné!" else "Vous avez perdu..")
                return true
            }
        }

        // colonnes
        for (i in 0..2) {
            if (gameArrays[0][i] == gameArrays[1][i] && gameArrays[1][i] == gameArrays[2][i] && gameArrays[0][i] != EMPTY) {
                showEndGameDialog(if (gameArrays[0][i] == PLAYER) "Vous avez gagné!" else "Vous avez perdu..")
                return true
            }
        }

        // diagonales
        if ((gameArrays[0][0] == gameArrays[1][1] && gameArrays[1][1] == gameArrays[2][2] && gameArrays[0][0] != EMPTY) ||
            (gameArrays[0][2] == gameArrays[1][1] && gameArrays[1][1] == gameArrays[2][0] && gameArrays[0][2] != EMPTY)
        ) {
            showEndGameDialog(if (gameArrays[1][1] == PLAYER) "Vous avez gagné!" else "Vous avez perdu..")
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

    private fun setupGameGrid(size: Int) {
        gridLayout = binding.gameGrid
        gridLayout.columnCount = size

        for (i in 0 until size) {
            for (j in 0 until size) {

                val button = ImageButton(requireContext())
                val buttonId = "$i$j"
                button.id = buttonId.toInt()
                button.scaleType = ImageView.ScaleType.FIT_CENTER
                button.setOnClickListener {

                    if (gameArrays[i][j] == EMPTY) {
                        button.setImageResource(Player.getImageResource())
                        gameArrays[i][j] = PLAYER
                        if (!checkGameEnd()) {
                            playComputerMove()
                        }
                    } else {
                        showAlertDialog()
                    }
                }

                val params = GridLayout.LayoutParams()
                params.width = 200
                params.height = 200
                button.layoutParams = params
                gridLayout.addView(button)
            }
        }
    }

    private fun playComputerMove() {
        val emptyCells = mutableListOf<Pair<Int, Int>>()
        for (i in 0..2) {
            for (j in 0..2) {
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
            checkGameEnd()
        }
    }

    private fun showAlertDialog() {
        val builder = AlertDialog.Builder(context)
            .setTitle("Erreur")
            .setMessage("Cette cellule n'est pas vide.")
            .setPositiveButton("Ok") { dialog, which ->
            }
        builder.show()
    }
}