package cl.bozz.nqueensdfs.utils

object DiagonalChecker {

    fun performDiagonalCheck(
        cellX: Int,
        cellY: Int,
        xDelta: Int,
        yDelta: Int,
        newAvailableCells: MutableSet<Int>,
        queenPositions: Set<Int>,
        boardSize: Int
    ): Boolean {
        // Prepare initial check, this way we don't have to avoid queen self-attack false positives
        var x = cellX + xDelta
        var y = cellY + yDelta

        // First good fit for a do-while I've seen in a long time!
        do {
            val diagCellInt = x * boardSize + y
            if (queenPositions.contains(diagCellInt)) {
                return true
            }
            newAvailableCells.remove(diagCellInt)
            x += xDelta
            y += yDelta
        } while (x in 0 until boardSize && y in 0 until boardSize)
        return false
    }
}