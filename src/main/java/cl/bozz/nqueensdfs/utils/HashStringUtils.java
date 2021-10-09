package cl.bozz.nqueensdfs.utils;

import cl.bozz.nqueensdfs.datamodels.NQueensCell;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
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

    public Set<Set<NQueensCell>> getAllRotations(final Set<Integer> queens, final int n) {
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

    public Set<Set<Integer>> getAllMirrors(final Set<Integer> queens, final int n) {
        final Set<Set<Integer>> results = new HashSet<>();
        results.add(queens);

        final Set<Integer> mirroredQueens1 = mirror(queens, true, false, n);
        results.add(mirroredQueens1);
        final Set<Integer> mirroredQueens2 = mirror(queens, false, true, n);
        results.add(mirroredQueens2);
        final Set<Integer> mirroredQueens3 = mirror(queens, true, true, n);
        results.add(mirroredQueens3);

        return results;
    }

    private Set<NQueensCell> rotateNinetyDegrees(final Set<Integer> queens, final int times, final int n) {
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

    private Set<Integer> mirror(
            final Set<Integer> queens,
            final boolean horizontally,
            final boolean vertically,
            final int n
    ) {
        // Just flips each queen around one or both axes.

        return queens.stream()
                .map(queen -> {
                    int queenX = queen / n;
                    int queenY = queen % n;

                    int newQueenX = horizontally ? (n - 1 - queenX) : queenX;
                    int newQueenY = vertically ? (n - 1 - queenY) : queenY;

                    return (newQueenX * n) + newQueenY;
                }).collect(Collectors.toCollection(HashSet::new));
    }

    /**
     * Generates all rotations of the given queens, then all the mirrors of all the rotations plus the original.
     */
    public Set<String> generateHashStrings(final Set<Integer> queens, final int n) {
        // This is slightly wasteful in that it doesn't check for repeats during generation. Worst-case scenario is a
        // board that's symmetrical on both axes, where rotations and mirroring do nothing... but that's still just 15
        // wasted board computations. Symmetry checks would be much more complicated and wasteful.
        final Set<String> result =  getAllMirrors(queens, n).stream()
                .map(mirroredQueens -> getAllRotations(mirroredQueens, n))
                .flatMap(Collection::stream)
                .map(this::generateHashString)
                .collect(Collectors.toCollection(TreeSet::new));

        Set<NQueensCell> nq = queens.stream().map(
                queen -> new NQueensCell(queen / n, queen % n)
        ).collect(Collectors.toSet());
        result.add(generateHashString(nq));

        return result;
    }

    private String generateHashString(final Set<NQueensCell> queenList) {
        final StringBuilder stringBuilder = new StringBuilder();

        for (final NQueensCell queenCell : queenList) {
            stringBuilder.append(queenCell);
        }

        return stringBuilder.toString();
    }
}
