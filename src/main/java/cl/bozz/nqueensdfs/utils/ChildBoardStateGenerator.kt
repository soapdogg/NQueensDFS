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
                    val queenPos = BooleanArray(boardSize * boardSize)
                    for(i in 0 until boardSize * boardSize) {
                        if (i == it || boardState.queenPositions.contains(i)) {
                            queenPos[i] = true
                        }
                    }
                    BoardState(queenPos.toTypedArray())
                }.toSet()
    }
}