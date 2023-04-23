package eu.mspi.filteredseedfabric.seedfilters;

import java.util.Random;

public class SeedFilterUtils {
    public static Position getStructurePos(int structureType, long seed, int regX, int regZ) {
        StructureConfig sconf = StructureConfig.getStructureConfig(structureType);

        switch (structureType) {
            case StructureConfig.DESERT_TEMPLE:
            case StructureConfig.VILLAGE:
            case StructureConfig.SHIPWRECK:
            case StructureConfig.RUINED_PORTAL:
            case StructureConfig.RUINED_PORTAL_NETHER:
                return getFeaturePos(sconf, seed, regX, regZ);

            case StructureConfig.TREASURE: {
                Random rnd = new Random(regX * 341873128712L + regZ * 132897987541L + seed + sconf.salt);
                if(rnd.nextFloat() < 0.01)
                    return new Position(regX * 16 + 9, regZ * 16 + 9);
                return Position.none();
            }

            case StructureConfig.FORTRESS: {
                Random rnd = new Random(regX * 341873128712L + regZ * 132897987541L + seed + sconf.salt);
                int x = (int) (((long) regX * sconf.regionSize + rnd.nextInt(sconf.chunkRange)) * 16);
                int z = (int) (((long) regZ * sconf.regionSize + rnd.nextInt(sconf.chunkRange)) * 16);

                if(rnd.nextInt(5) < 2)
                    return new Position(x, z);
                return Position.none();
            }

            case StructureConfig.BASTION: {
                Random rnd = new Random(regX * 341873128712L + regZ * 132897987541L + seed + sconf.salt);
                int x = (int) (((long) regX * sconf.regionSize + rnd.nextInt(sconf.chunkRange)) * 16);
                int z = (int) (((long) regZ * sconf.regionSize + rnd.nextInt(sconf.chunkRange)) * 16);

                if(rnd.nextInt(5) >= 2)
                    return new Position(x, z);
                return Position.none();
            }

            default:
                throw new RuntimeException("getStructurePos: Structure type not implemented.");
        }
    }
    public static Position getFeaturePos(StructureConfig config, long seed, int regX, int regZ) {
        Position pos = getFeatureChunkInRegion(config, seed, regX, regZ);
        pos.x = (int) (((long) regX * config.regionSize + pos.x) * 16);
        pos.z = (int) (((long) regZ * config.regionSize + pos.z) * 16);
        return pos;
    }
    public static Position getFeatureChunkInRegion(StructureConfig config, long seed, int regX, int regZ) {
        final long K = 0x5deece66dL;
        final long M = (1L << 48) - 1;
        final long b = 0xb;

        Position pos = new Position(0, 0);

        seed = seed + regX * 341873128712L + regZ * 132897987541L + config.salt;
        seed = (seed ^ K);

        seed = (seed * K + b) & M;
        pos.x = (int)(seed >> 17) % config.chunkRange;
        seed = (seed * K + b) & M;
        pos.x += (int)(seed >> 17) % config.chunkRange;

        seed = (seed * K + b) & M;
        pos.z = (int)(seed >> 17) % config.chunkRange;
        seed = (seed * K + b) & M;
        pos.z += (int)(seed >> 17) % config.chunkRange;

        pos.x >>= 1;
        pos.z >>= 1;

        return pos;
    }
}
