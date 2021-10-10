package cl.bozz.nqueensdfs.models;

import lombok.Value;

import java.util.HashSet;
import java.util.Set;

@Value
public class BoardState {
    Boolean[] queenPositionsA;
    Set<Integer> queenPositions;
    int size;

    public BoardState(
        Boolean [] queenPositions,
        int size
    ) {
        this.queenPositions = new HashSet<>();
        for (int i = 0; i < queenPositions.length; ++i) {
            if (queenPositions[i]) {
                this.queenPositions.add(i);
            }
        }
        this.queenPositionsA = queenPositions;
        this.size = size;
    }

    public Set<Integer> getQueenPositions() {
        return queenPositions;
    }

    public Boolean[] getQueenPositionsA() {
        return this.queenPositionsA;
    }

    public int getSize() {
        return this.size;
    }
}
