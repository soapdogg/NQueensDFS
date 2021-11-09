package cl.bozz.nqueensdfs.models

data class Board(
    val queenPositions: Set<Int>,
    val size: Int,
    val hashes: Set<String>
)
