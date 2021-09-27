package cl.bozz.nqueensdfs.models;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@EqualsAndHashCode
@RequiredArgsConstructor
@Value
public class Cell implements Comparable<Cell> {
    private final int x;
    private final int y;

    @Override
    public int compareTo(final Cell cell) {
        return x != cell.getX() ? cell.getX() - x : cell.getY() - y;
    }

    @Override
    public String toString() {
        return "[" + x + "," + y + "]";
    }
}
