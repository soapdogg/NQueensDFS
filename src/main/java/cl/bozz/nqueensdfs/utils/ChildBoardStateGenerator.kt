package cl.bozz.nqueensdfs.utils

import cl.bozz.nqueensdfs.models.BoardState

object ChildBoardStateGenerator {

    fun generateChildBoardStates(
        boardState: BoardState,
        boardSize: Int
    ): Set<BoardState> {
        val availableCells = mutableSetOf<BoardState>()
        for (i in 0 until boardSize * boardSize) {
            val isValidQueenPos = ValidQueenPositionDeterminer.isQueenPositionValid(
                i,
                boardSize,
                boardState.queenPositions
            )
            if (isValidQueenPos) {
                val copy = boardState.queenPositionsA.copyOf()
                copy[i] = true
                availableCells.add(BoardState(copy))
            }
        }
        return availableCells
    }
}