package eu.mspi.filteredseedfabric.seedfilters;

public class RandomEmulator {
    public long seed;

    public RandomEmulator(long seed) {
        this.seed = seed;
    }

    public int next(int bits) {
        seed = (seed * 0x5deece66dL + 0xb) & ((1L << 48) - 1);
        return (int) (seed >> (48 - bits));
    }

    public int nextInt(final int n) {
        int bits, val;
        final int m = n - 1;

        if((m & n) == 0) return (int) ((n * (long) next(31)) >> 31);

        do {
            bits = next(31);
            val = bits % n;
        }
        while (bits - val + m < 0);
        return val;
    }

    public long nextLong() {
        return ((long) next(32) << 32) + next(32);
    }

    public float nextFloat() {
        return next(24) / (float) (1 << 24);
    }

    public double nextDouble() {
        return (((long) next(26) << 27) + next(27)) / (double) (1L << 53);
    }
}
