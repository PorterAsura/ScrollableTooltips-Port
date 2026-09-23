package club.sk1er.mods.scrollabletooltips.mixin;

import club.sk1er.mods.scrollabletooltips.TooltipScroller;
import club.sk1er.mods.scrollabletooltips.mixin.accessors.AccessorHandledScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.client.MouseHandler.class)
public class MouseMixin_CaptureScrolling {

    @Unique
    private static final String scrollableTooltips$mouseScrolledTarget =
        "Lnet/minecraft/client/gui/screens/Screen;mouseScrolled(DDDD)Z";

    @Inject(
        method = "onScroll(JDD)V",
        at = @At(value = "INVOKE", target = scrollableTooltips$mouseScrolledTarget),
        cancellable = true
    )
    private void scrollableTooltips$captureScroll(
        long window,
        double xOffset,
        double yOffset,
        CallbackInfo ci
    ) {
        Minecraft minecraft = Minecraft.getInstance();
        Screen currentScreen = minecraft.gui.screen();

        if (minecraft.player == null || currentScreen == null) return;

        if (currentScreen instanceof AbstractContainerScreen<?>) {
            AccessorHandledScreen containerScreen = (AccessorHandledScreen) currentScreen;
            Slot hoveredSlot = containerScreen.getHoveredSlot();

            if (hoveredSlot != null && hoveredSlot.hasItem()) {
                if (TooltipScroller.scroll(yOffset)) {
                    ci.cancel();
                }
            }
        }
    }
}