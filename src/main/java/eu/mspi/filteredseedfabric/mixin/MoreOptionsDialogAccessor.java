package eu.mspi.filteredseedfabric.mixin;

import net.minecraft.client.gui.screen.world.MoreOptionsDialog;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.world.gen.GeneratorOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.OptionalLong;

@Mixin(MoreOptionsDialog.class)
public interface MoreOptionsDialogAccessor {
    @Accessor
    TextFieldWidget getSeedTextField();

    @Invoker("tryParseLong")
    OptionalLong invokeTryParseLong(String string);

    @Accessor
    GeneratorOptions getGeneratorOptions();
}
