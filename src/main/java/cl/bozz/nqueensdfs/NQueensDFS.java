package cl.bozz.nqueensdfs;

import cl.bozz.nqueensdfs.utils.*;

import java.time.Instant;

public class NQueensDFS {
    public static void main(final String[] args) {

        final int n = 11;
        final Instant start = Instant.now();

        DFSOrchestrator.INSTANCE.orchestrateDFS(n);

        final Instant end = Instant.now();
        final long elapsedMillis = end.toEpochMilli() - start.toEpochMilli();
        System.out.println("Time elapsed (millis): " + elapsedMillis);
    }
}
