package eu.mspi.filteredseedfabric.seedfilters;

import com.seedfinding.mcbiome.source.NetherBiomeSource;
import com.seedfinding.mccore.version.MCVersion;

public class NetherFilter extends SeedFilter {
    /* Has to be either POS_NEG or NEG_POS or both (for now?) */
    public static final int FORTRESS_QUADRANT = Quadrant.POS_NEG | Quadrant.NEG_POS;

    public NetherFilter() {
        setFilterLowBits((seed) -> {
            /* First, check the bastion */
            RandomEmulator rnd = new RandomEmulator((seed + 30084232L) ^ 0x5deece66dL);

            long chunkx = rnd.next(31) % 23;
            long chunkz = rnd.next(31) % 23;
            boolean isBastion = (rnd.next(31) % 5) >= 2;

            if (chunkx > 8 || chunkz > 8 || !isBastion)
                return lowBitsTaken = false;

            /* Now check the bastion depending on FORTRESS_QUADRANT (might be a parameter in the future) */

            if((FORTRESS_QUADRANT & Quadrant.NEG_POS) != 0) {
                rnd.seed = (seed + 30084232L - 341873128712L) ^ 0x5deece66dL;
                chunkx = rnd.next(31) % 23;
                chunkz = rnd.next(31) % 23;

                boolean isFortress = (rnd.next(31) % 5) < 2;
                if(chunkx >= 19 && chunkz <= 8 && isFortress) {
                    structurePosition = new Position((int) chunkx, (int) chunkz);
                    return lowBitsTaken = true;
                }
            }

            if((FORTRESS_QUADRANT & Quadrant.POS_NEG) != 0) {
                rnd.seed = (seed + 30084232L - 132897987541L) ^ 0x5deece66dL;
                chunkx = rnd.next(31) % 23;
                chunkz = rnd.next(31) % 23;

                boolean isFortress = (rnd.next(31) % 5) < 2;
                if(chunkx <= 8 && chunkz >= 19 && isFortress) {
                    structurePosition = new Position((int) chunkx, (int) chunkz);
                    return lowBitsTaken = true;
                }
            }

            return lowBitsTaken = false;
        });

        setFilterHighBits((seed) -> {
            System.out.println("Trying nether biome");
            assert lowBitsTaken;

            /* Check that the biome where the bastion would be generated isn't Basalt Deltas (id 173) */
            NetherBiomeSource source = new NetherBiomeSource(MCVersion.v1_16_1, seed);
            if(source.getBiome(structurePosition.x * 16, 0, structurePosition.z * 16).getId() == 173)
                return false;

            System.out.println("Generated nether");
            return true;
        });
    }
}
