package eu.mspi.filteredseedfabric.seedfilters;

public class MagmaRavineFilter extends SeedFilter {
    public MagmaRavineFilter() {
        // TODO
        setFilterLowBits((seed) -> lowBitsTaken = true);
        setFilterHighBits((seed) -> true);
    }
}
