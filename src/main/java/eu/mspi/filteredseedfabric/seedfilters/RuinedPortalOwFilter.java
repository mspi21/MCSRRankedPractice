package eu.mspi.filteredseedfabric.seedfilters;

public class RuinedPortalOwFilter extends SeedFilter {
    public RuinedPortalOwFilter() {
        // TODO
        setFilterLowBits((seed) -> lowBitsTaken = true);
        setFilterHighBits((seed) -> true);
    }
}
