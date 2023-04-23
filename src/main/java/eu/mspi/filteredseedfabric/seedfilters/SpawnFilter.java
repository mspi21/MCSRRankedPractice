package eu.mspi.filteredseedfabric.seedfilters;

import com.seedfinding.mcbiome.source.OverworldBiomeSource;
import com.seedfinding.mccore.util.math.DistanceMetric;
import com.seedfinding.mccore.util.math.Vec3i;
import com.seedfinding.mccore.util.pos.BPos;
import com.seedfinding.mccore.util.pos.CPos;
import com.seedfinding.mccore.version.MCVersion;
import com.seedfinding.mcfeature.misc.SpawnPoint;

public class SpawnFilter extends SeedFilter {
    public SpawnFilter(Vec3i from, int radius) {
        setFilterLowBits((seed) -> lowBitsTaken = true);
        setFilterHighBits((seed) -> {
            System.out.print("Checking approximate spawn position: ");

            OverworldBiomeSource bs = new OverworldBiomeSource(MCVersion.v1_16, seed);
            BPos spawn = SpawnPoint.getApproximateSpawn(bs).toChunkPos().toBlockPos(0);

            System.out.print(spawn.getX());
            System.out.print(" ");
            System.out.print(spawn.getZ());
            System.out.println();

            if(spawn.distanceTo(from, DistanceMetric.EUCLIDEAN) > radius)
                return false;

            System.out.println("Spawn position OK");
            return true;
        });
    }
}
