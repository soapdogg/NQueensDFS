package cl.bozz.nqueensdfs.utils

import cl.bozz.nqueensdfs.NQueensDFS
import cl.bozz.nqueensdfs.datamodels.NQueensCell
import cl.bozz.nqueensdfs.models.BoardState

object BoardPrinter {

    fun printBoard(board: BoardState, boardSize: Int) {
        for (i in 0 until boardSize) {
            for (j in 0 until boardSize) {
                val cell = (i * boardSize) + j
                if (board.queenPositions.contains(cell)) {
                    print('Q')
                } else {
                    print('x')
                }
            }
            println()
        }
    }
}