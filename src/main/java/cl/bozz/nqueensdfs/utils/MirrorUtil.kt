package cl.bozz.nqueensdfs.utils

object MirrorUtil {

    fun getAllMirrors(queens: BooleanArray, n: Int): Set<Set<Int>> {
        val results: MutableSet<Set<Int>> = HashSet()
        val mirroredQueens0 = mirror(queens, horizontally = false, vertically = false, n = n)
        results.add(mirroredQueens0)
        val mirroredQueens1 = mirror(queens, horizontally = true, vertically = false, n = n)
        results.add(mirroredQueens1)
        val mirroredQueens2 = mirror(queens, horizontally = false, vertically = true, n = n)
        results.add(mirroredQueens2)
        val mirroredQueens3 = mirror(queens, horizontally = true, vertically = true, n = n)
        results.add(mirroredQueens3)
        return results
    }

    private fun mirror(
        queens: BooleanArray,
        horizontally: Boolean,
        vertically: Boolean,
        n: Int
    ): Set<Int> {
        // Just flips each queen around one or both axes.
        val result = mutableSetOf<Int>()
        for (i in 0 until n * n) {
            if (queens[i]) {
                val queenX = i / n
                val queenY = i % n
                val newQueenX = if (horizontally) n - 1 - queenX else queenX
                val newQueenY = if (vertically) n - 1 - queenY else queenY
                result.add(newQueenX * n + newQueenY)
            }
        }
        return result
    }
}