package eu.mspi.filteredseedfabric.seedfilters;

import java.util.function.Function;

public class SeedFilter {
    private Function<Long, Boolean> filterLowBits;
    private Function<Long, Boolean> filterHighBits;
    protected Position structurePosition;

    protected boolean lowBitsTaken;

    protected SeedFilter() {
        this.lowBitsTaken = false;
    }

    public void reset() {
        lowBitsTaken = false;
    }

    private SeedFilter(Function<Long, Boolean> filterLowBits, Function<Long, Boolean> filterHighBits) {
        this.filterLowBits = filterLowBits;
        this.filterHighBits = filterHighBits;
        this.structurePosition = Position.none();
    }

    protected void setFilterLowBits(Function<Long, Boolean> filterLowBits) {
        this.filterLowBits = filterLowBits;
    }
    protected void setFilterHighBits(Function<Long, Boolean> filterHighBits) {
        this.filterHighBits = filterHighBits;
    }

    boolean testLowBits(long seed) {
        return filterLowBits.apply(seed);
    }

    boolean testHighBits(long seed) {
        return filterHighBits.apply(seed);
    }

    /* Utility methods */

    public static SeedFilter makeAnd(SeedFilter first, SeedFilter... rest) {
        return new SeedFilter((seed) -> {
            if(!first.testLowBits(seed))
                return false;
            for (SeedFilter filter : rest)
                if(!filter.testLowBits(seed))
                    return false;
            return true;
        }, (seed) -> {
            if(first.lowBitsTaken && !first.testHighBits(seed))
                return false;
            for (SeedFilter filter : rest)
                if(first.lowBitsTaken && !filter.testHighBits(seed))
                    return false;
            return true;
        });
    }

    public static SeedFilter makeOr(SeedFilter first, SeedFilter... rest) {
        return new SeedFilter((seed) -> {
            if(first.testLowBits(seed))
                return true;
            for (SeedFilter filter : rest)
                if(filter.testLowBits(seed))
                    return true;
            return false;
        }, (seed) -> {
            if(first.lowBitsTaken && first.testHighBits(seed))
                return true;
            for (SeedFilter filter : rest)
                if(filter.lowBitsTaken && filter.testHighBits(seed))
                    return true;
            return false;
        });
    }
}
