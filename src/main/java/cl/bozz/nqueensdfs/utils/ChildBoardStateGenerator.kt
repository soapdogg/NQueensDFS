package cl.bozz.nqueensdfs.utils

import cl.bozz.nqueensdfs.models.Board

object ChildBoardStateGenerator {

    fun generateChildBoardStates(
            board: Board,
            boardSize: Int
    ): Set<Board> {
        val availableCells = mutableSetOf<Board>()
        for (i in 0 until boardSize * boardSize) {
            val isValidQueenPos = ValidQueenPositionDeterminer.isQueenPositionValid(
                    i,
                    boardSize,
                    board.queenPositions,
            )
            if (isValidQueenPos) {
                val copy = board.queenPositions.copyOf()
                copy[i] = true
                availableCells.add(Board(copy, board.size + 1))
            }
        }
        return availableCells
    }
}