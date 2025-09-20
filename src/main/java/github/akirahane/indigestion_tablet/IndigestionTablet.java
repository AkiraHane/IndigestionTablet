package github.akirahane.indigestion_tablet;

import com.mojang.logging.LogUtils;
import github.akirahane.indigestion_tablet.effect.ModEffects;
import github.akirahane.indigestion_tablet.item.ModItems;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(IndigestionTablet.MODID)
public class IndigestionTablet {
    public static final String MODID = "indigestion_tablet";
    private static final Logger LOGGER = LogUtils.getLogger();

    public IndigestionTablet() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // 注册我们的物品
        ModItems.register(modEventBus);
        // 注册我们的效果
        ModEffects.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);

        // 将物品添加到创造模式物品栏
        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Indigestion Tablet Mod has been set up!");
    }

    // 添加物品到创造模式物品栏
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        // 将健胃消食片添加到“食物与饮品”标签页
        if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
            event.accept(ModItems.INDIGESTION_TABLET);
        }
    }
}
