package eu.mspi.filteredseedfabric.seedfilters;

import com.seedfinding.mcbiome.biome.Biome;
import com.seedfinding.mcbiome.source.BiomeSource;
import com.seedfinding.mcbiome.source.OverworldBiomeSource;
import com.seedfinding.mccore.rand.ChunkRand;
import com.seedfinding.mccore.rand.seed.WorldSeed;
import com.seedfinding.mccore.util.pos.BPos;
import com.seedfinding.mccore.version.MCVersion;
import com.seedfinding.mcfeature.structure.RegionStructure;
import com.seedfinding.mcfeature.structure.Shipwreck;

public class ShipwreckFilter extends SeedFilter {
    public static final int QUADRANT = Quadrant.POS_POS;
    public static final boolean GOOD_SHIPS_ONLY = true;
    protected static final Shipwreck SHIPWRECK = new Shipwreck(MCVersion.v1_16_1);

    public ShipwreckFilter() {
        setFilterLowBits((seed) -> {
            Position pos = SeedFilterUtils.getStructurePos(
                    StructureConfig.SHIPWRECK,
                    seed & 0xFF_FF_FF_FF_FF_FFL,
                    Quadrant.x(QUADRANT),
                    Quadrant.z(QUADRANT)
            );

            if(!pos.valid || pos.x >= 96 || pos.z >= 96)
                return lowBitsTaken = false;

            if(GOOD_SHIPS_ONLY) {
                int shipChunkX = pos.x >> 4;
                int shipChunkZ = pos.z >> 4;

                RandomEmulator rnd = new RandomEmulator((seed) ^ 0x5deece66dL);
                long carveA = rnd.nextLong();
                long carveB = rnd.nextLong();

                rnd.seed = ((shipChunkX * carveA) ^ (shipChunkZ * carveB) ^ seed) ^ 0x5deece66dL;
                rnd.seed = rnd.seed & 0xFFFFFFFFFFFFL;
                rnd.seed = (0x5deece66dL * rnd.seed + 11) % (1L << 48); //advance 2
                rnd.seed = (0x5deece66dL * rnd.seed + 11) % (1L << 48);
                long shipType = (rnd.seed >> 17) % 20;

                //rejecting front only ships, allowing all others
                if(!(shipType != 2 && shipType != 5 && shipType != 8 && shipType != 12 && shipType != 15 && shipType != 18))
                    return lowBitsTaken = false;
            }

            structurePosition = pos;
            return lowBitsTaken = true;
        });

        setFilterHighBits((seed) -> {
            System.out.println("Trying biomes for shipwreck seed");
            assert lowBitsTaken;

            BiomeSource source = new OverworldBiomeSource(MCVersion.v1_16_1, seed);
            if(!SHIPWRECK.isValidBiome(source.getBiome(structurePosition.x, 0, structurePosition.z)))
                return false;

            System.out.print("Generated shipwreck seed with ship at ");
            System.out.print(structurePosition.x);
            System.out.print(", ");
            System.out.print(structurePosition.z);
            System.out.println(".");
            return true;
        });
    }
}
