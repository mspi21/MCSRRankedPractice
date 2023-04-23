package eu.mspi.filteredseedfabric;

import com.seedfinding.mcbiome.source.NetherBiomeSource;
import com.seedfinding.mccore.version.MCVersion;
import eu.mspi.filteredseedfabric.seedfilters.RandomEmulator;
import net.fabricmc.api.ModInitializer;

public class FilteredSeedFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        long testSeed = -5355832069006992575L;

        RandomEmulator rnd = new RandomEmulator((testSeed + 30084232L) ^ 0x5deece66dL);
        long chunkx = rnd.next(31) % 23;
        long chunkz = rnd.next(31) % 23;

        System.out.print("Bastion Chunk X: ");
        System.out.println(chunkx);
        System.out.print("Bastion Chunk Z: ");
        System.out.println(chunkz);

        NetherBiomeSource source = new NetherBiomeSource(MCVersion.v1_16_1, testSeed);

        System.out.print("Biome id: ");
        System.out.println(source.getBiome((int) chunkx * 16, 0, (int) chunkz * 16).getId());
    }
}
