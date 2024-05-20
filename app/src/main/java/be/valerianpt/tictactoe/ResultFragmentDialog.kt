package be.valerianpt.tictactoe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import be.valerianpt.tictactoe.databinding.FragmentGameBinding
import be.valerianpt.tictactoe.databinding.FragmentResultBinding

class ResultFragmentDialog : DialogFragment() {
    interface ResultFragmentListener {
        fun onRestartGame()
        fun onReturnToHome()
    }

    var resultFragmentListener: ResultFragmentListener? = null
    lateinit var binding: FragmentResultBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResultBinding.inflate(layoutInflater)
        val gameResult = arguments?.getInt("gameResult") ?: 0

        binding.resultTitle.text = when (gameResult) {
            1 -> "Vous avez gagné !"
            2 -> "Vous avez perdu.."
            else -> "Match nul"
        }

        binding.relaunchGameButton.setOnClickListener {
            resultFragmentListener?.onRestartGame()
            dismiss()
        }

        binding.homeButton.setOnClickListener {
            resultFragmentListener?.onReturnToHome()
            dismiss()
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        resultFragmentListener = null
    }

    // Méthode pour définir le listener
    fun setOnButtonClickListener(listener: ResultFragmentListener) {
        resultFragmentListener = listener
    }

    companion object {
        const val TAG = "ResultFragmentDialog"

        fun newInstance(gameResult: Int): ResultFragmentDialog {
            val fragment = ResultFragmentDialog()
            val bundle = Bundle().apply {
                putInt("gameResult", gameResult)
            }
            fragment.arguments = bundle
            return fragment
        }
    }
}