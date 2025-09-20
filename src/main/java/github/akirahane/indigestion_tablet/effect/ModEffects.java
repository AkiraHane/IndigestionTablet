package github.akirahane.indigestion_tablet.effect;

import github.akirahane.indigestion_tablet.IndigestionTablet;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, IndigestionTablet.MODID);

    // 注册“健胃消食”效果
    public static final RegistryObject<MobEffect> DIGESTION_AID = MOB_EFFECTS.register("digestion_aid",
            () -> new DigestionAidEffect(MobEffectCategory.BENEFICIAL, 0x98D982)); // 一个护眼的绿色

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
