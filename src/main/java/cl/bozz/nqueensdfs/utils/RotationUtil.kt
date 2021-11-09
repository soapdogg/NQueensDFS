package cl.bozz.nqueensdfs.utils

object RotationUtil {

    private val ROTATIONS = arrayOf(
        Pair(Pair(0, -1), Pair(1, 0)),
        Pair(Pair(-1, 0), Pair(0, -1)),
        Pair(Pair(0, 1), Pair(-1, 0))
    )

    fun getAllRotations(queens: Set<Int>, boardSize: Int): Set<BooleanArray> {
        val results: MutableSet<BooleanArray> = HashSet()
        val boolArray = BooleanArray(boardSize * boardSize)
        for (i in queens) {
            boolArray[i] = true
        }

        results.add(boolArray)
        for (i in 0..2) {
            val rotatedQueens1 = rotateNinetyDegrees(queens, i, boardSize)
            results.add(rotatedQueens1)
        }
        return results
    }

    private fun rotateNinetyDegrees(queens: Set<Int>, times: Int, n: Int): BooleanArray {
        // Since the center of an even-sided board will be in the middle of a NQueensCell, we need to use decimals.
        // We use (n - 1) because cell positions start at 0 and end at (n - 1).
        val center = (n - 1).toDouble() / 2

        val (x, y) = ROTATIONS[times]
        val boolArray = BooleanArray(n * n)
        for (i in queens) {
                val cellX = i / n
                val cellY = i % n
                val baseX = cellX.toDouble() - center
                val baseY = cellY.toDouble() - center
                val rotatedX = baseX * x.first + baseY * y.first + center
                val rotatedY = baseX * x.second + baseY * y.second + center
                val rotatedCell = rotatedX.toInt() * n +  rotatedY.toInt()
                boolArray[rotatedCell] = true
        }
        return boolArray
    }
}