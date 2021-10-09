package cl.bozz.nqueensdfs.utils;

import cl.bozz.nqueensdfs.models.Cell;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class HashStringUtils {
    // Rotation constants. Should have used 4 maps instead of using Cell as a tuple. Eh.
    private static final Map<Integer, Cell> ROTATIONS_X = new HashMap<Integer, Cell>() {{
        put(1, new Cell(0, -1));
        put(2, new Cell(-1, 0));
        put(3, new Cell(0, 1));
    }};
    private static final Map<Integer, Cell> ROTATIONS_Y = new HashMap<Integer, Cell>() {{
        put(1, new Cell(1, 0));
        put(2, new Cell(0, -1));
        put(3, new Cell(-1, 0));
    }};

    public Set<Set<Cell>> getAllRotations(final Set<Cell> queens, final int n) {
        final Set<Set<Cell>> results = new HashSet<>();
        results.add(queens);

        final Set<Cell> rotatedQueens1 = rotateNinetyDegrees(queens, 1, n);
        if (rotatedQueens1 != null) {
            results.add(rotatedQueens1);
        }
        final Set<Cell> rotatedQueens2 = rotateNinetyDegrees(queens, 2, n);
        if (rotatedQueens2 != null) {
            results.add(rotatedQueens2);
        }
        final Set<Cell> rotatedQueens3 = rotateNinetyDegrees(queens, 3, n);
        if (rotatedQueens3 != null) {
            results.add(rotatedQueens3);
        }

        return results;
    }

    public Set<Set<Cell>> getAllMirrors(final Set<Cell> queens, final int n) {
        final Set<Set<Cell>> results = new HashSet<>();
        results.add(queens);

        final Set<Cell> mirroredQueens1 = mirror(queens, true, false, n);
        if (mirroredQueens1 != null) {
            results.add(mirroredQueens1);
        }
        final Set<Cell> mirroredQueens2 = mirror(queens, false, true, n);
        if (mirroredQueens2 != null) {
            results.add(mirroredQueens2);
        }
        final Set<Cell> mirroredQueens3 = mirror(queens, true, true, n);
        if (mirroredQueens3 != null) {
            results.add(mirroredQueens3);
        }

        return results;
    }

    private Set<Cell> rotateNinetyDegrees(final Set<Cell> queens, final int times, final int n) {
        // Since the center of an even-sided board will be in the middle of a cell, we need to use decimals.
        // We use (n - 1) because cell positions start at 0 and end at (n - 1).
        final double center = (double)(n - 1) / 2;

        // I still think using Cell for this is stupid, but I don't want to bother with fixing it.
        final Cell rotationX = ROTATIONS_X.get(times);
        final Cell rotationY = ROTATIONS_Y.get(times);

        final Set<Cell> newQueens = queens.stream()
                .map(cell -> {
                    // New cell = (Old cell - center) * rotation + center.
                    // Some basic geometry to get the rotation coefficients:
                    // x_prime = x * cos(theta) - y * sin(theta)
                    // y_prime = x * sin(theta) + y * cos(theta)
                    final double baseX = (double)cell.getX() - center;
                    final double baseY = (double)cell.getY() - center;

                    final double rotatedX = baseX * rotationX.getX() + baseY * rotationX.getY() + center;
                    final double rotatedY = baseX * rotationY.getX() + baseY * rotationY.getY() + center;

                    return new Cell((int) rotatedX, (int)rotatedY);
                }).collect(Collectors.toCollection(HashSet::new));

        return newQueens;
    }

    private Set<Cell> mirror(
            final Set<Cell> queens,
            final boolean horizontally,
            final boolean vertically,
            final int n
    ) {
        // Just flips each queen around one or both axes.
        final Set<Cell> newQueens = queens.stream()
                .map(queen -> new Cell(
                        horizontally ? (n - 1 - queen.getX()) : queen.getX(),
                        vertically ? (n - 1 - queen.getY()) : queen.getY()
                )).collect(Collectors.toCollection(HashSet::new));

        return newQueens;
    }

    /**
     * Generates all rotations of the given queens, then all the mirrors of all the rotations plus the original.
     * @param queens
     * @param n
     * @return
     */
    public Set<String> generateHashStrings(final Set<Cell> queens, final int n) {
        // This is slightly wasteful in that it doesn't check for repeats during generation. Worst-case scenario is a
        // board that's symmetrical on both axes, where rotations and mirroring do nothing... but that's still just 15
        // wasted board computations. Symmetry checks would be much more complicated and wasteful.
        final Set<String> result =  getAllMirrors(queens, n).stream()
                .map(mirroredQueens -> getAllRotations(mirroredQueens, n))
                .flatMap(Collection::stream)
                .map(this::generateHashString)
                .collect(Collectors.toCollection(TreeSet::new));

        result.add(generateHashString(queens));

        return result;
    }

    private String generateHashString(final Set<Cell> queenList) {
        final StringBuilder stringBuilder = new StringBuilder();

        for (final Cell queenCell : queenList) {
            stringBuilder.append(queenCell);
        }

        return stringBuilder.toString();
    }
}
