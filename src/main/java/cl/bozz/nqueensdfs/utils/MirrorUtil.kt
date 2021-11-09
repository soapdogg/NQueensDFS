package cl.bozz.nqueensdfs.utils

object MirrorUtil {

    fun getAllMirrors(queens: Set<Int>, n: Int): List<Set<Int>> {
        val mirroredQueens0 = mirror(queens, horizontally = false, vertically = false, n = n)
        val mirroredQueens1 = mirror(queens, horizontally = true, vertically = false, n = n)
        val mirroredQueens2 = mirror(queens, horizontally = false, vertically = true, n = n)
        val mirroredQueens3 = mirror(queens, horizontally = true, vertically = true, n = n)
        return listOf(
            mirroredQueens0,
            mirroredQueens1,
            mirroredQueens2,
            mirroredQueens3,
        )
    }

    private fun mirror(
        queens: Set<Int>,
        horizontally: Boolean,
        vertically: Boolean,
        n: Int
    ): Set<Int> {
        // Just flips each queen around one or both axes.
        val result = mutableSetOf<Int>()

        for (i in queens) {

                val queenX = i / n
                val queenY = i % n
                val newQueenX = if (horizontally) n - 1 - queenX else queenX
                val newQueenY = if (vertically) n - 1 - queenY else queenY
                result.add(newQueenX * n + newQueenY)
        }
        return result
    }
}