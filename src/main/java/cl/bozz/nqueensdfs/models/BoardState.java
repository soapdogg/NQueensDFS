package cl.bozz.nqueensdfs.models;

import lombok.Value;

import java.util.Set;

@Value
public class BoardState {
    Set<Integer> queenPositions;

    public BoardState(
        Set<Integer> queenPositions
    ) {
        this.queenPositions = queenPositions;
    }

    public Set<Integer> getQueenPositions() {
        return queenPositions;
    }
}
