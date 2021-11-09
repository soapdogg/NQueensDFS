package cl.bozz.nqueensdfs.utils

object HashStringUtils {

    fun generateHashStrings(mirroredAndRotatedBoards: List<Set<Int>>): Set<String> {
        // This is slightly wasteful in that it doesn't check for repeats during generation. Worst-case scenario is a
        // board that's symmetrical on both axes, where rotations and mirroring do nothing... but that's still just 15
        // wasted board computations. Symmetry checks would be much more complicated and wasteful.
        return mirroredAndRotatedBoards
                .map { obj -> generateHashString(obj) }.toSet()
    }

    private fun generateHashString(queenList: Set<Int>): String {
        return queenList.joinToString("-")
    }
}