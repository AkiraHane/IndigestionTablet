package github.akirahane.indigestion_tablet.item;

import github.akirahane.indigestion_tablet.IndigestionTablet;
import github.akirahane.indigestion_tablet.effect.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, IndigestionTablet.MODID);

    // 注册“健胃消食片”物品
    public static final RegistryObject<Item> INDIGESTION_TABLET = ITEMS.register("indigestion_tablet",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
                    .alwaysEat() // 饱食度满时也能吃
                    .fast()      // 快速吃完
                    // 吃完后给予10分钟 (10 * 60 * 20 = 12000 ticks) 的健胃消食I效果
                    .effect(() -> new MobEffectInstance(ModEffects.DIGESTION_AID.get(), 12000, 0, false, false), 1.0F)
                    .build())));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
