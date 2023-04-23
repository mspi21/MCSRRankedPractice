package eu.mspi.filteredseedfabric.seedfilters;

public class Quadrant {
    public static final int POS_POS = 0x1;
    public static final int POS_NEG = 0x2;
    public static final int NEG_POS = 0x4;
    public static final int NEG_NEG = 0x8;

    public static int x(int q) {
        return (q == POS_POS || q == POS_NEG) ? 0 : -1;
    }

    public static int z(int q) {
        return (q == POS_POS || q == NEG_POS) ? 0 : -1;
    }
}
