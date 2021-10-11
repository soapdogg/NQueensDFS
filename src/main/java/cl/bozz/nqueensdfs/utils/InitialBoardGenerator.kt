package cl.bozz.nqueensdfs.utils

import cl.bozz.nqueensdfs.models.Board

object InitialBoardGenerator {

    fun generateInitialBoard(boardSize: Int): Board {
        return Board(BooleanArray(boardSize * boardSize), 0)
    }
}