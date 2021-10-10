package cl.bozz.nqueensdfs.utils

import cl.bozz.nqueensdfs.datamodels.NQueensCell
import java.util.stream.Collectors

object RotationUtil {

    private val ROTATIONS = arrayOf(
        Pair(Pair(0, -1), Pair(1, 0)),
        Pair(Pair(-1, 0), Pair(0, -1)),
        Pair(Pair(0, 1), Pair(-1, 0))
    )

    fun getAllRotations(queens: Set<Int>, n: Int): Set<Set<NQueensCell>> {
        val results: MutableSet<Set<NQueensCell>> = HashSet()
        val nq = queens.stream().map { queen: Int -> NQueensCell(queen / n, queen % n) }.collect(Collectors.toSet())
        results.add(nq)
        for (i in 0..2) {
            val rotatedQueens1 = rotateNinetyDegrees(queens, i, n)
            results.add(rotatedQueens1)
        }
        return results
    }

    private fun rotateNinetyDegrees(queens: Set<Int>, times: Int, n: Int): Set<NQueensCell> {
        // Since the center of an even-sided board will be in the middle of a NQueensCell, we need to use decimals.
        // We use (n - 1) because cell positions start at 0 and end at (n - 1).
        val center = (n - 1).toDouble() / 2

        // I still think using Cell for this is stupid, but I don't want to bother with fixing it.
        val (x, y) = ROTATIONS[times]
        return queens.stream()
                .map { cell: Int ->
                    // New cell = (Old cell - center) * rotation + center.
                    // Some basic geometry to get the rotation coefficients:
                    // x_prime = x * cos(theta) - y * sin(theta)
                    // y_prime = x * sin(theta) + y * cos(theta)
                    val cellX = cell / n
                    val cellY = cell % n
                    val baseX = cellX.toDouble() - center
                    val baseY = cellY.toDouble() - center
                    val rotatedX = baseX * x.first + baseY * y.first + center
                    val rotatedY = baseX * x.second + baseY * y.second + center
                    NQueensCell(rotatedX.toInt(), rotatedY.toInt())
                }.collect(Collectors.toCollection { HashSet() })
    }
}