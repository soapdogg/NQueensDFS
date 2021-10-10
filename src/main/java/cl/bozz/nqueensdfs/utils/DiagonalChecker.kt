package cl.bozz.nqueensdfs.utils

object DiagonalChecker {

    fun performDiagonalCheck(
            cellX: Int,
            cellY: Int,
            xDelta: Int,
            yDelta: Int,
            queenPositions: Array<Boolean>,
            boardSize: Int
    ): Boolean {
        // Prepare initial check, this way we don't have to avoid queen self-attack false positives
        var x = cellX + xDelta
        var y = cellY + yDelta

        while (x in 0 until boardSize && y in 0 until boardSize) {
            val diagCellInt = x * boardSize + y
            if (queenPositions[diagCellInt]) {
                return true
            }
            x += xDelta
            y += yDelta
        }
        return false
    }
}