package cl.bozz.nqueensdfs.utils

import cl.bozz.nqueensdfs.models.Board

object BoardPrinter {

    fun printBoard(board: Board, boardSize: Int) {
        for (i in 0 until boardSize) {
            for (j in 0 until boardSize) {
                val cell = (i * boardSize) + j
                if (board.queenPositions[cell]) {
                    print('Q')
                } else {
                    print('x')
                }
            }
            println()
        }
        println()
    }
}