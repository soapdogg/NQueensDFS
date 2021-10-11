package cl.bozz.nqueensdfs.utils

object TotalPermutationsCalculator {
    fun totalPermutations(boardSize: Int): Long {
        val totalCells = boardSize * boardSize
        var result = totalCells.toLong()
        for (i in 1 until boardSize) {
            val mult = totalCells - i
            result *= mult
        }
        return result
    }
}