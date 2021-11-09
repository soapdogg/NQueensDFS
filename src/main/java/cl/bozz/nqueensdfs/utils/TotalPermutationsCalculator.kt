package cl.bozz.nqueensdfs.utils

object TotalPermutationsCalculator {
    fun totalPermutations(boardSize: UInt): ULong {
        val totalCells = boardSize * boardSize
        var result = totalCells.toULong()
        for (i in 1.toUInt() until boardSize) {
            val mult = totalCells - i
            result *= mult
        }
        return result
    }
}