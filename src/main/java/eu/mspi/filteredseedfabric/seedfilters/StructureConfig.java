package eu.mspi.filteredseedfabric.seedfilters;

public class StructureConfig {
    public final int salt;
    public final int regionSize;
    public final int chunkRange;
    public final int structType;
    public final int properties;

    public static final int DESERT_TEMPLE = 1;
    public static final int VILLAGE = 5;
    public static final int SHIPWRECK = 7;
    public static final int RUINED_PORTAL = 11;
    public static final int RUINED_PORTAL_NETHER = 12;
    public static final int TREASURE = 14;
    public static final int FORTRESS = 16;
    public static final int BASTION = 17;

    public static final int STRUCT_TRIANGULAR   = 0x01;
    public static final int STRUCT_CHUNK        = 0x02;
    public static final int STRUCT_NETHER       = 0x10;
    public static final int STRUCT_END          = 0x20;

    public StructureConfig(int salt, int regionSize, int chunkRange, int structType, int properties) {
        this.salt = salt;
        this.regionSize = regionSize;
        this.chunkRange = chunkRange;
        this.structType = structType;
        this.properties = properties;
    }

    public static StructureConfig getStructureConfig(int structureType) {
        switch (structureType) {
            case DESERT_TEMPLE:
                return new StructureConfig(14357617, 32, 24, DESERT_TEMPLE, 0);
            case VILLAGE:
                return new StructureConfig(10387312, 32, 24, VILLAGE, 0);
            case SHIPWRECK:
                return new StructureConfig(165745295, 15, 7, SHIPWRECK, 0);
            case RUINED_PORTAL:
                return new StructureConfig(34222645, 40, 25, RUINED_PORTAL, 0);
            case RUINED_PORTAL_NETHER:
                return new StructureConfig(34222645, 25, 15, RUINED_PORTAL_NETHER, STRUCT_NETHER);
            case TREASURE:
                return new StructureConfig(10387320, 1, 1, TREASURE, STRUCT_CHUNK);
            case FORTRESS:
                return new StructureConfig(30084232, 27, 23, FORTRESS, STRUCT_NETHER);
            case BASTION:
                return new StructureConfig(30084232, 27, 23, BASTION, STRUCT_NETHER);
            default:
                throw new RuntimeException("getStructureConfig: Required structure not implemented.");
        }
    }
}
