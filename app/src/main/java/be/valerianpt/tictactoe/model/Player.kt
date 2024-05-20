package be.valerianpt.tictactoe.model

import be.valerianpt.tictactoe.R

// singleton
object Player {
    var isCircleSymbol = true

    fun getImageResource(): Int =
        if (isCircleSymbol) R.drawable.circle_symbol_purple else R.drawable.x_symbol_purple
}
