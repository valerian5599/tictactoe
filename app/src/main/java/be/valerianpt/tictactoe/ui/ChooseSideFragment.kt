package be.valerianpt.tictactoe.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import be.valerianpt.tictactoe.R
import be.valerianpt.tictactoe.databinding.FragmentChooseSideBinding
import be.valerianpt.tictactoe.model.Player

class ChooseSideFragment : Fragment() {
    private lateinit var binding: FragmentChooseSideBinding
    private var isCircleSymbol = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChooseSideBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        selectButtonUI()

        binding.xButton.setOnClickListener {
            isCircleSymbol = false
            selectButtonUI()
        }

        binding.oButton.setOnClickListener {
            isCircleSymbol = true
            selectButtonUI()
        }

        binding.launchGameButton.setOnClickListener {
            Player.isCircleSymbol = isCircleSymbol
            findNavController().navigate(ChooseSideFragmentDirections.actionChooseSideFragmentToGameFragment())
        }

        binding.homeButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun selectButtonUI() {
        if (!isCircleSymbol) {
            binding.xButton.background = ContextCompat.getDrawable(
                requireContext(),
                R.drawable.round_button
            )
            binding.xButton.setImageResource(R.drawable.x_symbol_purple)

            binding.oButton.background =
                ContextCompat.getDrawable(requireContext(), android.R.color.transparent)
            binding.oButton.setImageResource(R.drawable.circle_symbol)
        } else {
            binding.oButton.background = ContextCompat.getDrawable(
                requireContext(),
                R.drawable.round_button
            )
            binding.oButton.setImageResource(R.drawable.circle_symbol_purple)

            binding.xButton.background =
                ContextCompat.getDrawable(requireContext(), android.R.color.transparent)
            binding.xButton.setImageResource(R.drawable.x_symbol)
        }
    }
}