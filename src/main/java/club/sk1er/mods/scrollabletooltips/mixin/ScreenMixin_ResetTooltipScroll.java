package club.sk1er.mods.scrollabletooltips.mixin;

import club.sk1er.mods.scrollabletooltips.TooltipScroller;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public class ScreenMixin_ResetTooltipScroll {
    @Inject(method = "onClose", at = @At("HEAD"))
    private void scrollableTooltips$resetTooltipScroll(CallbackInfo ci) {
        TooltipScroller.resetScroll();
    }
}