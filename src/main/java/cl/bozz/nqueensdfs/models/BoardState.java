package cl.bozz.nqueensdfs.models;

import cl.bozz.nqueensdfs.datamodels.NQueensCell;
import lombok.Value;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Value
public class BoardState {
    private final Set<Integer> queenPositions;
    private final Set<Integer> availableCells;

    public Set<Integer> getQueenPositions() {
        return queenPositions;
    }

    /**
     * Create a new board that's identical to this one, but with one additional queen in a given position. Returns null
     * if a new board is not viable.
     */
    public BoardState addQueen(final NQueensCell cell, final int boardSize) {
        // Edge cases to terminate recursive DFS. Some are redundant, but who cares?
        int cellInt = (cell.getX() * boardSize) + cell.getY();
        if (
                queenPositions.size() == boardSize             // Terminal board, can't add more queens
                || availableCells.isEmpty()            // Can't add any more queens due to space or attack constraints
                || !availableCells.contains(cellInt)      // Trying to put a queen in an unavailable cell
                || queenPositions.contains(cell)       // This queen already exists
                || cell.getX() < 0 || cell.getX() >= boardSize // Out of bounds - X
                || cell.getY() < 0 || cell.getY() >= boardSize // Out of bounds - Y
        ) {
            return null;
        }

        // Prepare new available cells, minus new queen
        final Set<Integer> newAvailableCells = new HashSet<>();
        newAvailableCells.addAll(availableCells);
        newAvailableCells.remove(cell);

        // Try to remove vertical/horizontal rows under the new queen's range
        for (int i = 0; i < boardSize; i ++) {
            final NQueensCell horizontalCell = new NQueensCell(i, cell.getY());
            final int horizontalCellInt = (i * boardSize) + cell.getY();
            final NQueensCell verticalCell = new NQueensCell(cell.getX(), i);
            final int verticalCellInt = (cell.getX() * boardSize) + i;
            // We do this *before* updating the queen list to avoid self-attack false positives
            if (
                    queenPositions.contains(horizontalCell)
                    || queenPositions.contains(verticalCell)
            ) {
                // Attacking another queen, discard
                return null;
            }

            newAvailableCells.remove(horizontalCellInt);
            newAvailableCells.remove(verticalCellInt);
        }

        // Try to remove diagonal rows under the new queen's range
        if (
                !performDiagonalCheck(cell, -1, -1, newAvailableCells, boardSize)
                || !performDiagonalCheck(cell, 1, -1, newAvailableCells, boardSize)
                || !performDiagonalCheck(cell, -1, 1, newAvailableCells, boardSize)
                || !performDiagonalCheck(cell, 1, 1, newAvailableCells, boardSize)
        ) {
            return null;
        }

        // Copy queen positions, plus new queen
        final Set<Integer> newQueenPositions = new HashSet<>();
        newQueenPositions.addAll(queenPositions);
        newQueenPositions.add(cellInt);

        // Done!
        return new BoardState(newQueenPositions, newAvailableCells);
    }

    private boolean performDiagonalCheck(
            final NQueensCell cell,
            final int xDelta,
            final int yDelta,
            final Set<Integer> newAvailableCells,
            final int boardSize
    ) {
        // Prepare initial check, this way we don't have to avoid queen self-attack false positives
        int x = cell.getX() + xDelta;
        int y = cell.getY() + yDelta;

        // First good fit for a do-while I've seen in a long time!
        do {
            final NQueensCell diagCell = new NQueensCell (x, y);
            final int diagCellInt = (x * boardSize) + y;
            if (queenPositions.contains(diagCell)) {
                return false;
            }

            newAvailableCells.remove(diagCellInt);

            x += xDelta;
            y += yDelta;
        }
        while (
                x >= 0 && x < boardSize
                && y >= 0 && y < boardSize
        );

        return true;
    }

    /**
     * "Recursion" step: try to create new boards for every currently available cell.
     */
    public Set<BoardState> generateChildBoardStates(int boardSize) {
        final Set<BoardState> result = new HashSet<>();

        availableCells.stream()
                .map(availableCell -> addQueen(new NQueensCell(availableCell / boardSize, availableCell % boardSize) , boardSize))
                .filter(Objects::nonNull) // Filter out failed attempts
                .forEach(result::add);

        return result;
    }
}
