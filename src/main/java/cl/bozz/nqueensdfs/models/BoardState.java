package cl.bozz.nqueensdfs.models;

import cl.bozz.nqueensdfs.utils.DiagonalChecker;
import lombok.Value;

import java.util.HashSet;
import java.util.Set;

@Value
public class BoardState {
    private final Set<Integer> queenPositions;
    private final Set<Integer> availableCells;

    public Set<Integer> getAvailableCells() {
        return availableCells;
    }

    public Set<Integer> getQueenPositions() {
        return queenPositions;
    }

    /**
     * Create a new board that's identical to this one, but with one additional queen in a given position. Returns null
     * if a new board is not viable.
     */
    public BoardState addQueen(final int cell, final int boardSize) {
        // Edge cases to terminate recursive DFS. Some are redundant, but who cares?
        int cellX = cell / boardSize;
        int cellY = cell % boardSize;
        if (
                queenPositions.size() == boardSize             // Terminal board, can't add more queens
                || availableCells.isEmpty()            // Can't add any more queens due to space or attack constraints
                || !availableCells.contains(cell)      // Trying to put a queen in an unavailable cell
                || queenPositions.contains(cell)       // This queen already exists
                || cellX < 0 || cellX >= boardSize // Out of bounds - X
                || cellY < 0 || cellY >= boardSize // Out of bounds - Y
        ) {
            return null;
        }

        // Prepare new available cells, minus new queen
        final Set<Integer> newAvailableCells = new HashSet<>(availableCells);
        newAvailableCells.remove(cell);

        // Try to remove vertical/horizontal rows under the new queen's range
        for (int i = 0; i < boardSize; i ++) {
            final int horizontalCellInt = (i * boardSize) + cellY;
            final int verticalCellInt = (cellX * boardSize) + i;
            // We do this *before* updating the queen list to avoid self-attack false positives
            if (
                    queenPositions.contains(horizontalCellInt)
                    || queenPositions.contains(verticalCellInt)
            ) {
                // Attacking another queen, discard
                return null;
            }

            newAvailableCells.remove(horizontalCellInt);
            newAvailableCells.remove(verticalCellInt);
        }

        // Try to remove diagonal rows under the new queen's range
        if (
                DiagonalChecker.INSTANCE.cantPerformDiagonalCheck(cellX, cellY, -1, -1, newAvailableCells, queenPositions, boardSize)
                || DiagonalChecker.INSTANCE.cantPerformDiagonalCheck(cellX, cellY,  1, -1, newAvailableCells, queenPositions, boardSize)
                || DiagonalChecker.INSTANCE.cantPerformDiagonalCheck(cellX, cellY,  -1, 1, newAvailableCells, queenPositions, boardSize)
                || DiagonalChecker.INSTANCE.cantPerformDiagonalCheck(cellX, cellY,  1, 1, newAvailableCells, queenPositions, boardSize)
        ) {
            return null;
        }

        // Copy queen positions, plus new queen
        final Set<Integer> newQueenPositions = new HashSet<>(queenPositions);
        newQueenPositions.add(cell);

        // Done!
        return new BoardState(newQueenPositions, newAvailableCells);
    }
}
