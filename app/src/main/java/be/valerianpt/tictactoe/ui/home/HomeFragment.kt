package be.valerianpt.tictactoe.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import be.valerianpt.tictactoe.ui.game.ResultFragmentDialog
import be.valerianpt.tictactoe.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.startGameButton.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToChooseSideFragment())
        }

        binding.gameRulesButton.setOnClickListener {
            val rulesFragment = GameRulesFragment()
            rulesFragment.show(childFragmentManager, ResultFragmentDialog.TAG)
        }
    }
}