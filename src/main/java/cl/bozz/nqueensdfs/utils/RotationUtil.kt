package cl.bozz.nqueensdfs.utils

import cl.bozz.nqueensdfs.datamodels.NQueensCell
import java.util.function.Supplier
import java.util.stream.Collectors

object RotationUtil {

    // Rotation constants. Should have used 4 maps instead of using Cell as a tuple. Eh.
    private val ROTATIONS_X = arrayOf(
            NQueensCell(0, -1),
            NQueensCell(-1, 0),
            NQueensCell(0, 1)
    )
    private val ROTATIONS_Y = arrayOf(
            NQueensCell(1, 0),
            NQueensCell(0, -1),
            NQueensCell(-1, 0)
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
        val (x, y) = ROTATIONS_X[times]
        val (x1, y1) = ROTATIONS_Y[times]
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
                    val rotatedX = baseX * x + baseY * y + center
                    val rotatedY = baseX * x1 + baseY * y1 + center
                    NQueensCell(rotatedX.toInt(), rotatedY.toInt())
                }.collect(Collectors.toCollection(Supplier { HashSet() }))
    }
}