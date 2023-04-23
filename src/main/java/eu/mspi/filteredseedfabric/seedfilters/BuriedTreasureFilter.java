package eu.mspi.filteredseedfabric.seedfilters;

import com.seedfinding.mcbiome.source.OverworldBiomeSource;
import com.seedfinding.mccore.rand.ChunkRand;
import com.seedfinding.mccore.rand.seed.WorldSeed;
import com.seedfinding.mccore.version.MCVersion;
import com.seedfinding.mcfeature.structure.BuriedTreasure;
import com.seedfinding.mcfeature.structure.RegionStructure;

public class BuriedTreasureFilter extends SeedFilter {
    public static final int QUADRANT = Quadrant.POS_POS;
    public static final int MAX_DISTANCE = 96;

    protected static final BuriedTreasure BURIED_TREASURE = new BuriedTreasure(MCVersion.v1_16);

    public BuriedTreasureFilter() {
        setFilterLowBits((seed) -> {
            int nChunks = MAX_DISTANCE / 16 + 1;

            for(int i = Quadrant.x(QUADRANT) * nChunks; i < (Quadrant.x(QUADRANT) + 1) * nChunks; ++i)
                for(int j = Quadrant.z(QUADRANT) * nChunks; j < (Quadrant.z(QUADRANT) + 1) * nChunks; ++j)
                    if(SeedFilterUtils.getStructurePos(StructureConfig.TREASURE, seed, i, j).valid) {
                        structurePosition = new Position(i, j);
                        return lowBitsTaken = true;
                    }
            return lowBitsTaken = false;
        });

        setFilterHighBits((seed) -> {
            System.out.println("Trying biomes for BT seed");
            assert lowBitsTaken;

            ChunkRand rand = new ChunkRand();
            rand.setSeed(seed, false);

            RegionStructure.Data<BuriedTreasure> treasure = BURIED_TREASURE.at(structurePosition.x, structurePosition.z);

            // check that the structure can generate in that chunk (it's luck based with a nextFloat)
            if(!treasure.testStart(WorldSeed.toStructureSeed(seed), rand))
                return false;

            // test if the biomes are correct at that place
            OverworldBiomeSource source = new OverworldBiomeSource(MCVersion.v1_16_1, seed);
            if(!treasure.testBiome(source))
                return false;

            // if we ever want to check loot, do it here:

            /*
                long lootTableSeed = rand.nextLong();
                LootContext context = new LootContext(lootTableSeed, MCVersion.v1_16_1);
                List<ItemStack> loot = MCLootTables.BURIED_TREASURE_CHEST.get().generate(context);
                if(loot...) ...
            */

            System.out.print("Generated BT seed with BT at ");
            System.out.print(structurePosition.x);
            System.out.print(", ");
            System.out.print(structurePosition.z);
            System.out.println(".");
            return true;
        });
    }
}
