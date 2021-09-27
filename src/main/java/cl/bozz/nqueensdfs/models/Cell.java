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
        if (cell.getX() > x) {
            return 1;
        } else if (cell.getX() == x) {
            if (cell.getY() > y) {
                return 1;
            } else if (cell.getY() == y) {
                return 0;
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }

    @Override
    public String toString() {
        return "[" + x + "," + y + "]";
    }
}
