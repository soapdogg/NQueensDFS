package cl.bozz.nqueensdfs.models;

import lombok.Value;

import java.util.HashSet;
import java.util.Set;

@Value
public class BoardState {
    Boolean[] queenPositionsA;
    Set<Integer> queenPositions;

    public BoardState(
        Boolean [] queenPositions
    ) {
        this.queenPositions = new HashSet<>();
        for (int i = 0; i < queenPositions.length; ++i) {
            if (queenPositions[i]) {
                this.queenPositions.add(i);
            }
        }
        this.queenPositionsA = queenPositions;
    }

    public Set<Integer> getQueenPositions() {
        return queenPositions;
    }

    public Boolean[] getQueenPositionsA() {
        return this.queenPositionsA;
    }
}
