package be.valerianpt.tictactoe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        //nav
        binding.homeButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}