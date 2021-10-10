package cl.bozz.nqueensdfs.utils

import cl.bozz.nqueensdfs.utils.MirrorUtil.getAllMirrors
import cl.bozz.nqueensdfs.utils.RotationUtil.getAllRotations

object HashStringUtils {
    /**
     * Generates all rotations of the given queens, then all the mirrors of all the rotations plus the original.
     */
    fun generateHashStrings(queens: Set<Int>, n: Int): Set<String> {
        // This is slightly wasteful in that it doesn't check for repeats during generation. Worst-case scenario is a
        // board that's symmetrical on both axes, where rotations and mirroring do nothing... but that's still just 15
        // wasted board computations. Symmetry checks would be much more complicated and wasteful.
        return getAllMirrors(queens, n)
                .map { mirroredQueens: Set<Int> -> getAllRotations(mirroredQueens, n) }
                .flatMap { it.toSet() }
                .map { obj: Set<Pair<Int, Int>> -> generateHashString(obj) }.toSet()
    }

    private fun generateHashString(queenList: Set<Pair<Int, Int>>): String {
        var result = ""
        for (queenCell in queenList) {
            result += queenCell
        }
        return result
    }
}