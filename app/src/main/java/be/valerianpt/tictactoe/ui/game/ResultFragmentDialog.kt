package be.valerianpt.tictactoe.ui.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import be.valerianpt.tictactoe.R
import be.valerianpt.tictactoe.databinding.FragmentResultBinding

class ResultFragmentDialog : DialogFragment() {
    interface ResultFragmentListener {
        fun onRestartGame()
        fun onReturnToHome()
    }

    private lateinit var binding: FragmentResultBinding
    private var resultFragmentListener: ResultFragmentListener? = null
    private var gameResult: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResultBinding.inflate(layoutInflater)

        // récupération des données
        gameResult = arguments?.getInt("gameResult") ?: 0

        setupUI()
        setupButton()

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        resultFragmentListener = null
    }


    private fun setupUI() {
        binding.resultTitle.text = when (gameResult) {
            1 -> getString(R.string.win_result_title)
            2 -> getString(R.string.lost_result_title)
            else -> getString(R.string.draw_result_title)
        }

        val imageSrc = when (gameResult) {
            1 -> R.drawable.trophy
            2 -> R.drawable.sad
            else -> R.drawable.equal
        }
        binding.resultImage.setImageResource(imageSrc)
    }

    private fun setupButton() {
        binding.relaunchGameButton.setOnClickListener {
            resultFragmentListener?.onRestartGame()
            dismiss()
        }

        binding.backHomeButton.setOnClickListener {
            resultFragmentListener?.onReturnToHome()
            dismiss()
        }
    }

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