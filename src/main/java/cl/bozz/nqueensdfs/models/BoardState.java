package cl.bozz.nqueensdfs.models;

import lombok.Value;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

@Value
public class BoardState {
    private final Set<Cell> queenPositions;
    private final Set<Cell> availableCells;
    private final int n;

    /**
     * Create a new board that's identical to this one, but with one additional queen in a given position. Returns null
     * if a new board is not viable.
     * @param cell
     * @return
     */
    public BoardState addQueen(final Cell cell) {
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
        final Set<Cell> newAvailableCells = new HashSet<Cell>();
        newAvailableCells.addAll(availableCells);
        newAvailableCells.remove(cell);

        // Try to remove vertical/horizontal rows under the new queen's range
        for (int i = 0; i < n; i ++) {
            final Cell horizontalCell = new Cell(i, cell.getY());
            final Cell verticalCell = new Cell(cell.getX(), i);
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
        final Set<Cell> newQueenPositions = new HashSet<>();
        newQueenPositions.addAll(queenPositions);
        newQueenPositions.add(cell);

        // Done!
        return new BoardState(newQueenPositions, newAvailableCells, n);
    }

    private boolean performDiagonalCheck(
            final Cell cell,
            final int xDelta,
            final int yDelta,
            final Set<Cell> newAvailableCells
    ) {
        // Prepare initial check, this way we don't have to avoid queen self-attack false positives
        int x = cell.getX() + xDelta;
        int y = cell.getY() + yDelta;

        // First good fit for a do-while I've seen in a long time!
        do {
            final Cell diagCell = new Cell (x, y);
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
                final Cell cell = new Cell(x, y);
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
