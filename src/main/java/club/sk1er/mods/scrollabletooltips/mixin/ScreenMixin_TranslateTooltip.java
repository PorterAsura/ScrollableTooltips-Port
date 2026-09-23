package club.sk1er.mods.scrollabletooltips.mixin;

import club.sk1er.mods.scrollabletooltips.TooltipScroller;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.Slot;
import org.joml.Vector2ic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiGraphicsExtractor.class)
public class ScreenMixin_TranslateTooltip {

    @Unique
    private static Slot scrollableTooltips$lastSlot;

    @Inject(
        method = "tooltip",
        at = @At(
            value = "INVOKE",
            target = "Lorg/joml/Vector2ic;x()I",
            shift = At.Shift.BEFORE
        )
    )
    private void scrollableTooltips$prepareTooltip(
        net.minecraft.client.gui.Font font,
        java.util.List<net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent> components,
        int mouseX,
        int mouseY,
        net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner positioner,
        net.minecraft.resources.Identifier texture,
        CallbackInfo ci
    ) {
        Minecraft minecraft = Minecraft.getInstance();

        if (minecraft.gui.screen() instanceof AbstractContainerScreen<?> containerScreen) {
            Slot hoveredSlot = ((club.sk1er.mods.scrollabletooltips.mixin.accessors.AccessorHandledScreen) containerScreen).getHoveredSlot();

            if (scrollableTooltips$lastSlot != hoveredSlot) {
                scrollableTooltips$lastSlot = hoveredSlot;
                TooltipScroller.resetScroll();
            }
        }
    }

    @Inject(
        method = "tooltip",
        at = @At(
            value = "INVOKE",
            target = "Lorg/joml/Matrix3x2fStack;pushMatrix()Lorg/joml/Matrix3x2fStack;",
            shift = At.Shift.AFTER
        )
    )
    private void scrollableTooltips$translateTooltip(
        net.minecraft.client.gui.Font font,
        java.util.List<net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent> components,
        int mouseX,
        int mouseY,
        net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner positioner,
        net.minecraft.resources.Identifier texture,
        CallbackInfo ci
    ) {
        GuiGraphicsExtractor graphics = (GuiGraphicsExtractor) (Object) this;

        int tooltipWidth = 0;
        int tooltipHeight = components.size() == 1 ? -2 : 0;

        for (net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent component : components) {
            tooltipWidth = Math.max(tooltipWidth, component.getWidth(font));
            tooltipHeight += component.getHeight(font);
        }

        Vector2ic position = positioner.positionTooltip(
            graphics.guiWidth(),
            graphics.guiHeight(),
            mouseX,
            mouseY,
            tooltipWidth,
            tooltipHeight
        );

        TooltipScroller.translateTooltip(
            graphics.pose(),
            position.x(),
            position.y(),
            tooltipWidth,
            tooltipHeight
        );
    }
}