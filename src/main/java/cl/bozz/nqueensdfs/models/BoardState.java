package cl.bozz.nqueensdfs.models;

import lombok.Value;

@Value
public class BoardState {
    Boolean[] queenPositions;
    int size;

    public BoardState(
        Boolean [] queenPositions,
        int size
    ) {
        this.queenPositions = queenPositions;
        this.size = size;
    }


    public Boolean[] getQueenPositions() {
        return this.queenPositions;
    }

    public int getSize() {
        return this.size;
    }
}
