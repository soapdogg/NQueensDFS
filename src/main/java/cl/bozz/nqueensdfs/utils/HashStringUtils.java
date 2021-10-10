package cl.bozz.nqueensdfs.utils;

import cl.bozz.nqueensdfs.datamodels.NQueensCell;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class HashStringUtils {
    /**
     * Generates all rotations of the given queens, then all the mirrors of all the rotations plus the original.
     */
    public static Set<String> generateHashStrings(final Set<Integer> queens, final int n) {
        // This is slightly wasteful in that it doesn't check for repeats during generation. Worst-case scenario is a
        // board that's symmetrical on both axes, where rotations and mirroring do nothing... but that's still just 15
        // wasted board computations. Symmetry checks would be much more complicated and wasteful.
        final Set<String> result =  MirrorUtil.INSTANCE.getAllMirrors(queens, n).stream()
                .map(mirroredQueens -> RotationUtil.INSTANCE.getAllRotations(mirroredQueens, n))
                .flatMap(Collection::stream)
                .map(HashStringUtils::generateHashString)
                .collect(Collectors.toSet());

        Set<NQueensCell> nq = queens.stream().map(
                queen -> new NQueensCell(queen / n, queen % n)
        ).collect(Collectors.toSet());
        result.add(generateHashString(nq));

        return result;
    }

    private static String generateHashString(final Set<NQueensCell> queenList) {
        String result = "";

        for (final NQueensCell queenCell : queenList) {
            result += queenCell;
        }

        return result;
    }
}
