package club.sk1er.mods.scrollabletooltips;

//#if FORGE
//$$ import net.minecraftforge.fml.common.Mod;
//$$ import net.minecraftforge.client.ConfigScreenHandler;
//$$ import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
//#if MC==11902
//$$ import net.minecraftforge.fml.ModLoadingContext;
//#endif
//$$ @Mod(ScrollableTooltips.MOD_ID)
//#elseif NEOFORGE
//$$ import net.neoforged.fml.common.Mod;
//$$ import net.neoforged.fml.ModLoadingContext;
//$$ import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
//$$ @Mod(ScrollableTooltips.MOD_ID)
//#endif
public class ScrollableTooltips {
    public static final String MOD_ID = "text_overflow_scroll";
    public static final String MOD_VERSION = "1.4.2";
    public static final String MOD_NAME = "Scrollable Tooltips";

    //#if FORGE
    //$$ public ScrollableTooltips(FMLJavaModLoadingContext context) {
    //#if MC==11902
    //$$     ModLoadingContext.get().registerExtensionPoint(
    //#else
    //$$     context.registerExtensionPoint(
    //#endif
    //$$         ConfigScreenHandler.ConfigScreenFactory.class,
    //$$         () -> new ConfigScreenHandler.ConfigScreenFactory(
    //$$             (minecraft, screen) -> Config.INSTANCE.gui()
    //$$         )
    //$$     );
    //$$ }
    //#elseif NEOFORGE
    //$$ public ScrollableTooltips() {
    //$$     ModLoadingContext.get().registerExtensionPoint(
    //$$         IConfigScreenFactory.class,
    //$$         () -> (minecraft, screen) -> Config.INSTANCE.gui()
    //$$     );
    //$$ }
    //#endif
}
