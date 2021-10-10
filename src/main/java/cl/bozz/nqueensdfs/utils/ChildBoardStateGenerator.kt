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
                    boardState.queenPositions,
                    boardState.size,
            )
            if (isValidQueenPos) {
                val copy = boardState.queenPositions.copyOf()
                copy[i] = true
                availableCells.add(BoardState(copy, boardState.size + 1))
            }
        }
        return availableCells
    }
}