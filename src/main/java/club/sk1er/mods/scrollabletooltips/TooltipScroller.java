package club.sk1er.mods.scrollabletooltips;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.joml.Matrix3x2fStack;
import org.lwjgl.glfw.GLFW;

public class TooltipScroller {
    private static final int TOOLTIP_PADDING_HORIZONTAL = 4;
    private static final int TOOLTIP_PADDING_VERTICAL = 1;
    private static final int SCREEN_MARGIN = 6;

    public static boolean needsReset;
    public static boolean allowScrolling;
    public static boolean tooltipNeedsScrolling;
    public static double scrollX = 0;
    public static double scrollY = 0;
    public static float zoomFactor = 1.0f;

    public static Object currentSlot;

    public static void translateTooltip(
            Matrix3x2fStack matrixStack,
            int tooltipX,
            int tooltipY,
            int tooltipWidth,
            int tooltipHeight
    ) {
        Screen screen = Minecraft.getInstance().gui.screen();

        if (screen == null) {
            return;
        }

        int scaledTooltipHeight = (int) (tooltipHeight * zoomFactor);
        int scaledTooltipWidth = (int) (tooltipWidth * zoomFactor);

        if (needsReset) {
            scrollX = 0;
            allowScrolling = true;

            int totalPadding = 2 * (TOOLTIP_PADDING_VERTICAL + SCREEN_MARGIN);

            tooltipNeedsScrolling =
                    (Math.max(scaledTooltipHeight, tooltipHeight) + totalPadding) > screen.height ||
                    Math.abs(tooltipY) + Math.max(scaledTooltipHeight, tooltipHeight) > screen.height;

            if (Config.startAtTop && tooltipNeedsScrolling) {
                scrollY = SCREEN_MARGIN - tooltipY;
            } else {
                scrollY = 0;
            }

            zoomFactor = 1.0f;
            needsReset = false;
        }

        if (!Config.masterToggle) {
            return;
        }

        if (allowScrolling) {
            int maxY = SCREEN_MARGIN - tooltipY;
            int minY = screen.height - tooltipY - scaledTooltipHeight - SCREEN_MARGIN;

            if (minY <= maxY) {
                if (scrollY > maxY) {
                    scrollY = maxY;
                } else if (scrollY < minY) {
                    scrollY = minY;
                }
            } else {
                if (scrollY < maxY) {
                    scrollY = maxY;
                }

                if (scrollY > minY) {
                    scrollY = minY;
                }
            }

            int maxX = TOOLTIP_PADDING_HORIZONTAL - tooltipX;
            int minX = screen.width - tooltipX - scaledTooltipWidth - TOOLTIP_PADDING_HORIZONTAL;

            if (minX <= maxX) {
                if (scrollX > maxX) {
                    scrollX = maxX;
                } else if (scrollX < minX) {
                    scrollX = minX;
                }
            } else {
                if (scrollX < maxX) {
                    scrollX = maxX;
                }

                if (scrollX > minX) {
                    scrollX = minX;
                }
            }
        }

        matrixStack.translate((float) scrollX, (float) scrollY);
        matrixStack.scale(zoomFactor, zoomFactor);
    }

    public static void resetScroll() {
        needsReset = true;
        allowScrolling = false;
    }

    public static boolean scroll(double delta) {
        Minecraft minecraft = Minecraft.getInstance();

        if (minecraft.getWindow() == null) {
            return false;
        }

        if (InputConstants.isKeyDown(minecraft.getWindow(), GLFW.GLFW_KEY_LEFT_CONTROL)
                || InputConstants.isKeyDown(minecraft.getWindow(), GLFW.GLFW_KEY_RIGHT_CONTROL)) {
            if (Config.zoom) {
                zoomFactor *= (float) (1.0 + 0.1 * Math.signum(delta));
                return true;
            }
        }

        if (allowScrolling) {
            if (!tooltipNeedsScrolling) {
                if (!Config.enableScrollingSmallTooltips) {
                    return false;
                }

                boolean altDown =
                        InputConstants.isKeyDown(minecraft.getWindow(), GLFW.GLFW_KEY_LEFT_ALT)
                        || InputConstants.isKeyDown(minecraft.getWindow(), GLFW.GLFW_KEY_RIGHT_ALT);

                if (!altDown) {
                    return false;
                }
            }

            boolean shiftDown =
                    InputConstants.isKeyDown(minecraft.getWindow(), GLFW.GLFW_KEY_LEFT_SHIFT)
                    || InputConstants.isKeyDown(minecraft.getWindow(), GLFW.GLFW_KEY_RIGHT_SHIFT);

            if (shiftDown && Config.horizontalScrolling) {
                scrollX += 10 * Math.signum(delta);
                return true;
            }

            if (Config.verticalScrolling) {
                scrollY += 10 * Math.signum(delta);
                return true;
            }
        }

        return false;
    }
}