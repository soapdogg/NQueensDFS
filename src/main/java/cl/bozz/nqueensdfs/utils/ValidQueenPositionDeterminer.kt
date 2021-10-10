package cl.bozz.nqueensdfs.utils

import cl.bozz.nqueensdfs.models.BoardState
import cl.bozz.nqueensdfs.utils.DiagonalChecker.performDiagonalCheck

object ValidQueenPositionDeterminer {

    fun isQueenPositionValid(
        cell: Int,
        boardSize: Int,
        queenPositions: Set<Int>
    ): Boolean {
        // Edge cases to terminate recursive DFS. Some are redundant, but who cares?
        val cellX = cell / boardSize
        val cellY = cell % boardSize
        if (queenPositions.size == boardSize // Terminal board, can't add more queens
                || queenPositions.contains(cell) // This queen already exists
        ) {
            return false
        }

        // Try to remove vertical/horizontal rows under the new queen's range
        for (i in 0 until boardSize) {
            val horizontalCellInt = i * boardSize + cellY
            val verticalCellInt = cellX * boardSize + i
            // We do this *before* updating the queen list to avoid self-attack false positives
            if (queenPositions.contains(horizontalCellInt)
                    || queenPositions.contains(verticalCellInt)) {
                // Attacking another queen, discard
                return false
            }
        }

        // Try to remove diagonal rows under the new queen's range
        if (performDiagonalCheck(cellX, cellY, -1, -1, queenPositions, boardSize)
                || performDiagonalCheck(cellX, cellY, 1, -1,  queenPositions, boardSize)
                || performDiagonalCheck(cellX, cellY, -1, 1,  queenPositions, boardSize)
                || performDiagonalCheck(cellX, cellY, 1, 1,  queenPositions, boardSize)) {
            return false
        }

        return true
    }
}