package be.valerianpt.tictactoe.model

import be.valerianpt.tictactoe.R

object Computer {
    fun getImageResource(): Int =
        if (Player.isCircleSymbol) R.drawable.x_symbol_purple else R.drawable.circle_symbol_purple
}