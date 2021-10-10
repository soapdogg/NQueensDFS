package cl.bozz.nqueensdfs.utils

object TotalPermutationsCalculator {
    fun totalPermutations(boardSize: Long): Long {
        val totalCells = boardSize * boardSize
        var result = totalCells
        for (i in 1 until boardSize) {
            val mult = totalCells - i
            result *= mult
        }
        return result
    }
}