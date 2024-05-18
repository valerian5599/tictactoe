package be.valerianpt.tictactoe

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.navigation.fragment.findNavController
import be.valerianpt.tictactoe.databinding.FragmentChooseSideBinding
import be.valerianpt.tictactoe.databinding.FragmentHomeBinding

class ChooseSideFragment : Fragment() {
    private lateinit var binding: FragmentChooseSideBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChooseSideBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        selectButtonUI(false)

        binding.xButton.setOnClickListener {
            selectButtonUI(true)
        }

        binding.oButton.setOnClickListener {
            selectButtonUI(false)
        }

        binding.launchGameButton.setOnClickListener {
            findNavController().navigate(ChooseSideFragmentDirections.actionChooseSideFragmentToGameFragment())
        }

        binding.homeButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun selectButtonUI(isXButton: Boolean) {
        if (isXButton) {
            binding.xButton.background = ContextCompat.getDrawable(requireContext(), R.drawable.round_button)
            binding.xButton.setImageResource(R.drawable.x_symbol_purple)

            binding.oButton.background = ContextCompat.getDrawable(requireContext(), android.R.color.transparent)
            binding.oButton.setImageResource(R.drawable.circle_symbol)
        } else {
            binding.oButton.background = ContextCompat.getDrawable(requireContext(), R.drawable.round_button)
            binding.oButton.setImageResource(R.drawable.circle_symbol_purple)

            binding.xButton.background = ContextCompat.getDrawable(requireContext(), android.R.color.transparent)
            binding.xButton.setImageResource(R.drawable.x_symbol)
        }
    }
}