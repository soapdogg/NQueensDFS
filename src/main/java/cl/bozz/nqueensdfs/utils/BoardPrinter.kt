package cl.bozz.nqueensdfs.utils

import cl.bozz.nqueensdfs.NQueensDFS
import cl.bozz.nqueensdfs.datamodels.NQueensCell
import cl.bozz.nqueensdfs.models.BoardState

object BoardPrinter {

    fun printBoard(board: BoardState) {
        for (i in 0 until board.n) {
            for (j in 0 until board.n) {
                val cell = NQueensCell(i, j)
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