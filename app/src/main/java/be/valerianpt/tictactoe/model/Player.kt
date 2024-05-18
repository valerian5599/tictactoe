package be.valerianpt.tictactoe.model

import be.valerianpt.tictactoe.R

//Singleton
object Player {
    var isCircleSymbol = true

    fun getImageResource(): Int = if (isCircleSymbol) R.drawable.circle_symbol else R.drawable.x_symbol
}
