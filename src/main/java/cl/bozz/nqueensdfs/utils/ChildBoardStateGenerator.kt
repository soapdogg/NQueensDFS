package cl.bozz.nqueensdfs.utils

import cl.bozz.nqueensdfs.models.BoardState

object ChildBoardStateGenerator {

    fun generateChildBoardStates(
        boardState: BoardState,
        boardSize: Int
    ): Set<BoardState> {
        val availableCells = mutableSetOf<Int>()
        for (i in 0 until boardSize * boardSize) {
            if (!boardState.queenPositions.contains(i)) {
                availableCells.add(i)
            }
        }
        return availableCells
                .filter { availableCell: Int -> ValidQueenPositionDeterminer.isQueenPositionValid(
                        availableCell,
                        boardSize,
                        boardState.queenPositions
                )}.map {
                    BoardState(boardState.queenPositions + it)
                }.toSet()
    }
}