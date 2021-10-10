package cl.bozz.nqueensdfs.utils

import cl.bozz.nqueensdfs.utils.MirrorUtil.getAllMirrors
import cl.bozz.nqueensdfs.utils.RotationUtil.getAllRotations

object HashStringUtils {

    fun generateHashStringsFromMirrors(mirroredQueens: Set<Set<Int>>, n: Int): Set<String> {
        // This is slightly wasteful in that it doesn't check for repeats during generation. Worst-case scenario is a
        // board that's symmetrical on both axes, where rotations and mirroring do nothing... but that's still just 15
        // wasted board computations. Symmetry checks would be much more complicated and wasteful.
        return mirroredQueens.map { mirroredQueen: Set<Int> -> getAllRotations(mirroredQueen, n) }
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