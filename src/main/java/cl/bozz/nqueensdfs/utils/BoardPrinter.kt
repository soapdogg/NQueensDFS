package cl.bozz.nqueensdfs.utils

object BoardPrinter {

    fun printBoard(queenPositions: BooleanArray, boardSize: Int) {
        for (i in 0 until boardSize) {
            for (j in 0 until boardSize) {
                val cell = (i * boardSize) + j
                if (queenPositions[cell]) {
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