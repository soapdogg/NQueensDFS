package cl.bozz.nqueensdfs.models;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@EqualsAndHashCode
@RequiredArgsConstructor
@Value
public class Cell {
    private final int x;
    private final int y;


}
