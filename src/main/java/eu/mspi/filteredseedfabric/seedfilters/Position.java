package eu.mspi.filteredseedfabric.seedfilters;

public class Position {
    public Position(int x, int z) {
        this.x = x;
        this.z = z;
        this.valid = true;
    }

    private Position(boolean valid) {
        this.valid = valid;
    }

    public static Position none() {
        return new Position(false);
    }

    public int x;
    public int z;
    public boolean valid;
}
