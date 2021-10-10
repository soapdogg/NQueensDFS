package cl.bozz.nqueensdfs.utils

import cl.bozz.nqueensdfs.utils.MirrorUtil.getAllMirrors
import cl.bozz.nqueensdfs.utils.RotationUtil.getAllRotations
import cl.bozz.nqueensdfs.utils.MirrorUtil
import cl.bozz.nqueensdfs.datamodels.NQueensCell
import cl.bozz.nqueensdfs.utils.RotationUtil
import cl.bozz.nqueensdfs.utils.HashStringUtils
import java.util.stream.Collectors

object HashStringUtils {
    /**
     * Generates all rotations of the given queens, then all the mirrors of all the rotations plus the original.
     */
    fun generateHashStrings(queens: Set<Int>, n: Int): Set<String> {
        // This is slightly wasteful in that it doesn't check for repeats during generation. Worst-case scenario is a
        // board that's symmetrical on both axes, where rotations and mirroring do nothing... but that's still just 15
        // wasted board computations. Symmetry checks would be much more complicated and wasteful.
        val result = getAllMirrors(queens, n).stream()
                .map { mirroredQueens: Set<Int> -> getAllRotations(mirroredQueens, n) }
                .flatMap { obj: Set<Set<NQueensCell>> -> obj.stream() }
                .map { obj: Set<NQueensCell> -> generateHashString(obj) }
                .collect(Collectors.toSet())
        val nq = queens.stream().map { queen: Int -> NQueensCell(queen / n, queen % n) }.collect(Collectors.toSet())
        result.add(generateHashString(nq))
        return result
    }

    private fun generateHashString(queenList: Set<NQueensCell>): String {
        var result = ""
        for (queenCell in queenList) {
            result += queenCell
        }
        return result
    }
}