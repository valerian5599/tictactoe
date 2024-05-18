package be.valerianpt.tictactoe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.ImageView
import androidx.core.content.ContextCompat
import be.valerianpt.tictactoe.databinding.FragmentGameBinding
import be.valerianpt.tictactoe.databinding.FragmentHomeBinding
import be.valerianpt.tictactoe.model.Player

class GameFragment : Fragment() {
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
    }

    private fun setupGameGrid(size: Int) {
        gridLayout = binding.gameGrid
        gridLayout.columnCount = size

        for (i in 0 until size) {
            for (j in 0 until size) {

                val button = ImageButton(requireContext())
                button.setOnClickListener {
                    button.setImageResource(Player.getImageResource())
                    button.scaleType = ImageView.ScaleType.FIT_CENTER
                }

                val params = GridLayout.LayoutParams()
                params.width = 200
                params.height = 200
                button.layoutParams = params
                gridLayout.addView(button)
            }
        }
    }
}