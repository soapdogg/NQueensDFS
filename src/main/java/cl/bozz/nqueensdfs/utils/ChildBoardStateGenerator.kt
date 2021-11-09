package cl.bozz.nqueensdfs.utils

import cl.bozz.nqueensdfs.models.Board

object ChildBoardStateGenerator {

    fun generateChildBoardStates(
        board: Board,
        boardSize: Int
    ): Set<Board> {
        val childBoardStates = mutableSetOf<Board>()
        for (i in 0 until boardSize * boardSize) {
            val isValidQueenPos = ValidQueenPositionDeterminer.isQueenPositionValid(
                i,
                boardSize,
                board.queenPositions,
            )
            if (isValidQueenPos) {
                val copy = HashSet(board.queenPositions)
                copy.add(i)
                val mirroredQueens = MirrorUtil.getAllMirrors(copy, boardSize)
                val mirroredAndRotatedQueens = mirroredQueens.map { mirroredQueen -> RotationUtil.getAllRotations(mirroredQueen, boardSize) }
                        .flatMap { it.toSet() }
                val mAndRSet = mirroredAndRotatedQueens.map {

                        val result = mutableSetOf<Int>()
                        for (i in it.indices) {
                            if (it[i]) {
                                result.add(i)
                            }
                        }
                        result

                }
                val childBoardHashes = HashStringUtils.generateHashStrings(mAndRSet)
                childBoardStates.add(Board(copy, board.size + 1, childBoardHashes))
            }
        }
        return childBoardStates
    }
}