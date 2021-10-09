package cl.bozz.nqueensdfs.utils

import cl.bozz.nqueensdfs.models.BoardState
import java.util.*

object ChildBoardStateGenerator {

    fun generateChildBoardStates(
        boardState: BoardState,
        boardSize: Int
    ): Set<BoardState> {
        val result: MutableSet<BoardState> = HashSet()
        boardState.availableCells
                .map { availableCell: Int -> boardState.addQueen(availableCell, boardSize) }
                .filter { obj: BoardState? -> Objects.nonNull(obj) } // Filter out failed attempts
                .forEach { e: BoardState -> result.add(e) }
        return result
    }
}