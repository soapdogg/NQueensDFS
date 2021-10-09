package cl.bozz.nqueensdfs.models;

import cl.bozz.nqueensdfs.datamodels.NQueensCell;
import lombok.Value;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

@Value
public class BoardState {
    private final Set<NQueensCell> queenPositions;
    private final Set<NQueensCell> availableCells;
    private final int n;

    /**
     * Create a new board that's identical to this one, but with one additional queen in a given position. Returns null
     * if a new board is not viable.
     */
    public BoardState addQueen(final NQueensCell cell) {
        // Edge cases to terminate recursive DFS. Some are redundant, but who cares?
        if (
                queenPositions.size() == n             // Terminal board, can't add more queens
                || availableCells.isEmpty()            // Can't add any more queens due to space or attack constraints
                || !availableCells.contains(cell)      // Trying to put a queen in an unavailable cell
                || queenPositions.contains(cell)       // This queen already exists
                || cell.getX() < 0 || cell.getX() >= n // Out of bounds - X
                || cell.getY() < 0 || cell.getY() >= n // Out of bounds - Y
        ) {
            return null;
        }

        // Prepare new available cells, minus new queen
        final Set<NQueensCell> newAvailableCells = new HashSet<NQueensCell>();
        newAvailableCells.addAll(availableCells);
        newAvailableCells.remove(cell);

        // Try to remove vertical/horizontal rows under the new queen's range
        for (int i = 0; i < n; i ++) {
            final NQueensCell horizontalCell = new NQueensCell(i, cell.getY());
            final NQueensCell verticalCell = new NQueensCell(cell.getX(), i);
            // We do this *before* updating the queen list to avoid self-attack false positives
            if (
                    queenPositions.contains(horizontalCell)
                    || queenPositions.contains(verticalCell)
            ) {
                // Attacking another queen, discard
                return null;
            }

            newAvailableCells.remove(horizontalCell);
            newAvailableCells.remove(verticalCell);
        }

        // Try to remove diagonal rows under the new queen's range
        if (
                !performDiagonalCheck(cell, -1, -1, newAvailableCells)
                || !performDiagonalCheck(cell, 1, -1, newAvailableCells)
                || !performDiagonalCheck(cell, -1, 1, newAvailableCells)
                || !performDiagonalCheck(cell, 1, 1, newAvailableCells)
        ) {
            return null;
        }

        // Copy queen positions, plus new queen
        final Set<NQueensCell> newQueenPositions = new HashSet<>();
        newQueenPositions.addAll(queenPositions);
        newQueenPositions.add(cell);

        // Done!
        return new BoardState(newQueenPositions, newAvailableCells, n);
    }

    private boolean performDiagonalCheck(
            final NQueensCell cell,
            final int xDelta,
            final int yDelta,
            final Set<NQueensCell> newAvailableCells
    ) {
        // Prepare initial check, this way we don't have to avoid queen self-attack false positives
        int x = cell.getX() + xDelta;
        int y = cell.getY() + yDelta;

        // First good fit for a do-while I've seen in a long time!
        do {
            final NQueensCell diagCell = new NQueensCell (x, y);
            if (queenPositions.contains(diagCell)) {
                return false;
            }

            newAvailableCells.remove(diagCell);

            x += xDelta;
            y += yDelta;
        }
        while (
                x >= 0 && x < n
                && y >= 0 && y < n
        );

        return true;
    }

    /**
     * "Recursion" step: try to create new boards for every currently available cell.
     * @return
     */
    public Set<BoardState> generateChildBoardStates() {
        final Set<BoardState> result = new HashSet<>();

        availableCells.stream()
                .map(this::addQueen)
                .filter(Objects::nonNull) // Filter out failed attempts
                .forEach(result::add);

        return result;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();

        for (int x = 0; x < n; x ++) {
            for (int y = 0; y < n; y ++) {
                final NQueensCell cell = new NQueensCell(x, y);
                if (queenPositions.contains(cell)) {
                    stringBuilder.append("Q");
                } else {
                    stringBuilder.append("x");
                }
            }
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }
}
