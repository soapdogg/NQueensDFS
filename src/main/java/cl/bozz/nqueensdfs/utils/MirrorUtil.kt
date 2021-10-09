package cl.bozz.nqueensdfs.utils

object MirrorUtil {

    fun getAllMirrors(queens: Set<Int>, n: Int): Set<Set<Int>> {
        val results: MutableSet<Set<Int>> = HashSet()
        results.add(queens)
        val mirroredQueens1 = mirror(queens, horizontally = true, vertically = false, n = n)
        results.add(mirroredQueens1)
        val mirroredQueens2 = mirror(queens, horizontally = false, vertically = true, n = n)
        results.add(mirroredQueens2)
        val mirroredQueens3 = mirror(queens, horizontally = true, vertically = true, n = n)
        results.add(mirroredQueens3)
        return results
    }

    private fun mirror(
        queens: Set<Int>,
        horizontally: Boolean,
        vertically: Boolean,
        n: Int
    ): Set<Int> {
        // Just flips each queen around one or both axes.
        return queens.map { queen: Int ->
            val queenX = queen / n
            val queenY = queen % n
            val newQueenX = if (horizontally) n - 1 - queenX else queenX
            val newQueenY = if (vertically) n - 1 - queenY else queenY
            newQueenX * n + newQueenY
        }.toSet()
    }
}