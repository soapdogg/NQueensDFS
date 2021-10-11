package cl.bozz.nqueensdfs.utils

import cl.bozz.nqueensdfs.models.Board

object ChildBoardStateGenerator {

    fun generateChildBoardStates(
        board: Board,
        boardSize: Int
    ): Set<Board> {
        val childBoardStates = mutableSetOf<Board>()
        for (i in board.queenPositions.indices) {
            val isValidQueenPos = ValidQueenPositionDeterminer.isQueenPositionValid(
                i,
                boardSize,
                board.queenPositions,
            )
            if (isValidQueenPos) {
                val copy = board.queenPositions.copyOf()
                copy[i] = true
                val mirroredQueens = MirrorUtil.getAllMirrors(copy, boardSize)
                val mirroredAndRotatedQueens = mirroredQueens.map { mirroredQueen: BooleanArray -> RotationUtil.getAllRotations(mirroredQueen, boardSize) }
                        .flatMap { it.toSet() }.toSet()
                val childBoardHashes = HashStringUtils.generateHashStrings(mirroredAndRotatedQueens)
                childBoardStates.add(Board(copy, board.size + 1, childBoardHashes))
            }
        }
        return childBoardStates
    }
}