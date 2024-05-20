package be.valerianpt.tictactoe.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import be.valerianpt.tictactoe.databinding.FragmentGameRulesBinding

class GameRulesFragment : DialogFragment() {
    private lateinit var binding: FragmentGameRulesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameRulesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // navigation
        binding.closeButton.setOnClickListener {
            dismiss()
        }
    }
}