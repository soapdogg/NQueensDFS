package cl.bozz.nqueensdfs.utils

object MirrorUtil {

    fun getAllMirrors(queens: BooleanArray, n: Int): Set<BooleanArray> {
        val mirroredQueens0 = mirror(queens, horizontally = false, vertically = false, n = n)
        val mirroredQueens1 = mirror(queens, horizontally = true, vertically = false, n = n)
        val mirroredQueens2 = mirror(queens, horizontally = false, vertically = true, n = n)
        val mirroredQueens3 = mirror(queens, horizontally = true, vertically = true, n = n)
        return setOf(
            mirroredQueens0,
            mirroredQueens1,
            mirroredQueens2,
            mirroredQueens3,
        )
    }

    private fun mirror(
        queens: BooleanArray,
        horizontally: Boolean,
        vertically: Boolean,
        n: Int
    ): BooleanArray {
        // Just flips each queen around one or both axes.
        val result = BooleanArray(n * n)

        for (i in queens.indices) {
            if (queens[i]) {
                val queenX = i / n
                val queenY = i % n
                val newQueenX = if (horizontally) n - 1 - queenX else queenX
                val newQueenY = if (vertically) n - 1 - queenY else queenY
                result[newQueenX * n + newQueenY] = true
            }
        }
        return result
    }
}