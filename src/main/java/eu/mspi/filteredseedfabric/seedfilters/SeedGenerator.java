package eu.mspi.filteredseedfabric.seedfilters;

import java.util.Collection;
import java.util.Random;

public class SeedGenerator {
    private static final Random rnd = new Random();

    public static long generateNext(SeedFilter filter) {
        long seed;

        do {
            seed = rnd.nextLong();
            filter.reset();
        } while (!filter.testLowBits(seed));

        do {
            seed = (seed & 0xFFFF_FFFF_FFFFL) | rnd.nextLong() << 48;
        } while (!filter.testHighBits(seed));

        return seed;
    }
}
