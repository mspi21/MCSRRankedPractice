package eu.mspi.filteredseedfabric.mixin;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Screen.class)
public class SinglePlayerButtonMixin {
    @Inject(
            method = "addButton",
            at = @At("HEAD")
    )
    private <T extends AbstractButtonWidget> void initSinglePlayerButton(T button, CallbackInfoReturnable<T> cir) {
        if(button.getMessage().equals(
            new TranslatableText("menu.singleplayer")
        )) {
            button.setMessage(new LiteralText("Filtered Seed Practice"));
        }
    }
}
