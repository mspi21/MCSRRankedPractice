package eu.mspi.filteredseedfabric.seedfilters;

import com.seedfinding.mcbiome.source.OverworldBiomeSource;
import com.seedfinding.mccore.rand.ChunkRand;
import com.seedfinding.mccore.rand.seed.WorldSeed;
import com.seedfinding.mccore.version.MCVersion;
import com.seedfinding.mcfeature.structure.BuriedTreasure;
import com.seedfinding.mcfeature.structure.RegionStructure;
import com.seedfinding.mcfeature.structure.Village;

import java.util.function.Function;

public class VillageFilter extends SeedFilter {
    public static final int QUADRANT = Quadrant.POS_POS;

    private static final Village VILLAGE = new Village(MCVersion.v1_16_1);

    public VillageFilter() {
        setFilterLowBits((seed) -> {
            Position pos = SeedFilterUtils.getStructurePos(
                    StructureConfig.VILLAGE,
                    seed & 0xFF_FF_FF_FF_FF_FFL,
                    Quadrant.x(QUADRANT),
                    Quadrant.z(QUADRANT)
            );

            // TODO for now we use hardcoded values copied from FSG
            if (pos.valid && (pos.x >= 96 || pos.z >= 96) && pos.x <= 144 && pos.z <= 144) {
                structurePosition = pos;
                return lowBitsTaken = true;
            }
            return lowBitsTaken = false;
        });

        setFilterHighBits((seed) -> {
            System.out.println("Trying biomes for village seed");
            assert lowBitsTaken;

            OverworldBiomeSource source = new OverworldBiomeSource(MCVersion.v1_16_1, seed);
            if(!VILLAGE.isValidBiome(source.getBiome(structurePosition.x, 70, structurePosition.z)))
                return false;

            System.out.print("Generated village seed with village at ");
            System.out.print(structurePosition.x);
            System.out.print(", ");
            System.out.print(structurePosition.z);
            System.out.println(".");
            return true;
        });
    }
}
