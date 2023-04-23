package eu.mspi.filteredseedfabric.seedfilters;

public class LavaPoolFilter extends SeedFilter {
    public LavaPoolFilter() {
        // TODO
        setFilterLowBits((seed) -> lowBitsTaken = true);
        setFilterHighBits((seed) -> true);
    }
}
