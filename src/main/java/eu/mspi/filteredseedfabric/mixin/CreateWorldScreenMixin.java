package eu.mspi.filteredseedfabric.mixin;

import com.seedfinding.mccore.util.math.Vec3i;
import eu.mspi.filteredseedfabric.seedfilters.*;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.screen.world.MoreOptionsDialog;
import net.minecraft.world.gen.GeneratorOptions;
import org.apache.commons.lang3.StringUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.OptionalLong;

@Mixin(CreateWorldScreen.class)
public class CreateWorldScreenMixin {
    @Redirect(
            method = "createLevel",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/screen/world/MoreOptionsDialog;getGeneratorOptions(Z)Lnet/minecraft/world/gen/GeneratorOptions;"
            )
    )
    public GeneratorOptions getGeneratorOptionsFSG(MoreOptionsDialog instance, boolean hardcore) {
        String inputSeed = ((MoreOptionsDialogAccessor) instance).getSeedTextField().getText();
        OptionalLong optionalLong;

        if (StringUtils.isEmpty(inputSeed)) {
            optionalLong = OptionalLong.of(generateOne(FilteredSeedOptions.FILTER_BT));
        }
        else {
            OptionalLong optionalLong2 = ((MoreOptionsDialogAccessor) instance).invokeTryParseLong(inputSeed);
            if (optionalLong2.isPresent() && optionalLong2.getAsLong() != 0L) {
                optionalLong = optionalLong2;
            }
            else {
                optionalLong = OptionalLong.of(inputSeed.hashCode());
            }
        }
        return ((MoreOptionsDialogAccessor) instance).getGeneratorOptions().withHardcore(hardcore, optionalLong);
    }

    private static long generateOne(@SuppressWarnings("SameParameterValue") int filterType) {
        switch (filterType) {
            case FilteredSeedOptions.FILTER_VILLAGE:
                return generateVillageSeed();

            case FilteredSeedOptions.FILTER_SHIPWRECK:
                return generateShipwreckSeed();

            case FilteredSeedOptions.FILTER_BT:
                return generateBTSeed();

            default:
            case FilteredSeedOptions.FILTER_ANY:
                return generateAnySeed();
        }
    }

    private static long generateAnySeed() {
        System.out.println("Generating any seed...");
        return SeedGenerator.generateNext(
                SeedFilter.makeAnd(
                        SeedFilter.makeOr(
                                SeedFilter.makeAnd(
                                        new VillageFilter(),
                                        SeedFilter.makeOr(
                                                new LavaPoolFilter(),
                                                new RuinedPortalOwFilter()
                                        )
                                ),
                                SeedFilter.makeAnd(
                                        new ShipwreckFilter(),
                                        new MagmaRavineFilter()
                                ),
                                SeedFilter.makeAnd(
                                        new BuriedTreasureFilter(),
                                        new MagmaRavineFilter()
                                )
                        ),
                        new NetherFilter(),
                        new SpawnFilter(new Vec3i(40, 0, 40), 100)
                )
        );
    }

    private static long generateVillageSeed() {
        System.out.println("Generating village seed...");
        return SeedGenerator.generateNext(
                SeedFilter.makeAnd(
                        new VillageFilter(),
                        SeedFilter.makeOr(
                                new LavaPoolFilter(),
                                new RuinedPortalOwFilter()
                        ),
                        new NetherFilter(),
                        new SpawnFilter(new Vec3i(40, 0, 40), 100)
                )
        );
    }

    private static long generateShipwreckSeed() {
        System.out.println("Generating shipwreck seed...");
        return SeedGenerator.generateNext(
                SeedFilter.makeAnd(
                        new ShipwreckFilter(),
                        new MagmaRavineFilter(),
                        new NetherFilter(),
                        new SpawnFilter(new Vec3i(40, 0, 40), 100)
                )
        );
    }

    private static long generateBTSeed() {
        System.out.println("Generating BT seed...");
        return SeedGenerator.generateNext(
                SeedFilter.makeAnd(
                        new BuriedTreasureFilter(),
                        new MagmaRavineFilter(),
                        new NetherFilter(),
                        new SpawnFilter(new Vec3i(40, 0, 40), 100)
                )
        );
    }
}
