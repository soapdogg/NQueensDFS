package cl.bozz.nqueensdfs.utils;

import cl.bozz.nqueensdfs.datamodels.NQueensCell;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class HashStringUtils {
    // Rotation constants. Should have used 4 maps instead of using Cell as a tuple. Eh.
    private static final NQueensCell[] ROTATIONS_X =  {
        new NQueensCell(0, -1),
        new NQueensCell(-1, 0),
        new NQueensCell(0, 1)
    };
    private static final NQueensCell[] ROTATIONS_Y = {
        new NQueensCell(1, 0),
        new NQueensCell(0, -1),
        new NQueensCell(-1, 0)
    };

    private static Set<Set<NQueensCell>> getAllRotations(final Set<Integer> queens, final int n) {
        final Set<Set<NQueensCell>> results = new HashSet<>();
        Set<NQueensCell> nq = queens.stream().map(
                queen -> new NQueensCell(queen / n, queen % n)
        ).collect(Collectors.toSet());
        results.add(nq);

        for (int i = 0; i <= 2; ++i) {
            Set<NQueensCell> rotatedQueens1 = rotateNinetyDegrees(queens, i, n);
            results.add(rotatedQueens1);
        }

        return results;
    }

    private static Set<NQueensCell> rotateNinetyDegrees(final Set<Integer> queens, final int times, final int n) {
        // Since the center of an even-sided board will be in the middle of a NQueensCell, we need to use decimals.
        // We use (n - 1) because cell positions start at 0 and end at (n - 1).
        final double center = (double)(n - 1) / 2;

        // I still think using Cell for this is stupid, but I don't want to bother with fixing it.
        final NQueensCell rotationX = ROTATIONS_X[times];
        final NQueensCell rotationY = ROTATIONS_Y[times];

        return queens.stream()
                .map(cell -> {
                    // New cell = (Old cell - center) * rotation + center.
                    // Some basic geometry to get the rotation coefficients:
                    // x_prime = x * cos(theta) - y * sin(theta)
                    // y_prime = x * sin(theta) + y * cos(theta)
                    int cellX = cell / n;
                    int cellY = cell % n;
                    final double baseX = (double)cellX - center;
                    final double baseY = (double)cellY - center;

                    final double rotatedX = baseX * rotationX.getX() + baseY * rotationX.getY() + center;
                    final double rotatedY = baseX * rotationY.getX() + baseY * rotationY.getY() + center;

                    return new NQueensCell((int) rotatedX, (int)rotatedY);
                }).collect(Collectors.toCollection(HashSet::new));
    }

    /**
     * Generates all rotations of the given queens, then all the mirrors of all the rotations plus the original.
     */
    public static Set<String> generateHashStrings(final Set<Integer> queens, final int n) {
        // This is slightly wasteful in that it doesn't check for repeats during generation. Worst-case scenario is a
        // board that's symmetrical on both axes, where rotations and mirroring do nothing... but that's still just 15
        // wasted board computations. Symmetry checks would be much more complicated and wasteful.
        final Set<String> result =  MirrorUtil.INSTANCE.getAllMirrors(queens, n).stream()
                .map(mirroredQueens -> getAllRotations(mirroredQueens, n))
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
