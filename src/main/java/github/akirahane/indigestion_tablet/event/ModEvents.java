package github.akirahane.indigestion_tablet.event;

import github.akirahane.indigestion_tablet.IndigestionTablet;
import github.akirahane.indigestion_tablet.effect.ModEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = IndigestionTablet.MODID)
public class ModEvents {

    // 监听玩家的每个tick
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        // 确保在服务器端执行，且在tick结束时执行以避免冲突
        if (event.phase == TickEvent.Phase.END && !event.player.level().isClientSide) {
            Player player = event.player;

            // 检查玩家是否有“健胃消食”效果
            if (player.hasEffect(ModEffects.DIGESTION_AID.get())) {
                FoodData foodData = player.getFoodData();

                // 记录是否触发效果
                boolean triggered = false;
                // 如果饱和度大于0
                if (foodData.getSaturationLevel() > 0) {
                    // 每刻消耗当前的25%饱和度 向上取整, 至少两点
                    int depSaturationLevel = Math.max((int) Math.ceil(foodData.getSaturationLevel() * 0.25), 2);
                    foodData.setSaturation(Math.max(foodData.getSaturationLevel() - depSaturationLevel, 0));
                    triggered = true;
                } else if (foodData.getFoodLevel() > 10) {  // 如果饱食度大于10 (即超过5个鸡腿)
                    // 每刻消耗一点饱食度
                    foodData.setFoodLevel(foodData.getFoodLevel() - 1);
                    triggered = true;
                }
                if (!triggered) {
                    return;
                }
                // 如果生命值不满，则回复一点
                if (player.getHealth() < player.getMaxHealth()) {
                    player.heal(1.0F);
                }
                // 否则，增加伤害吸收
                else {
                    float currentAbsorption = player.getAbsorptionAmount();
                    // 伤害吸收量上限为自己的血量上限
                    if (currentAbsorption < player.getMaxHealth()) {
                        player.setAbsorptionAmount(currentAbsorption + 1.0F);
                    }
                }
            }
        }
    }

    // 监听玩家使用完物品事件 (比如喝完药水)
    @SubscribeEvent
    public static void onDrinkWater(LivingEntityUseItemEvent.Finish event) {
        // 确认是玩家，且不是在客户端
        if (event.getEntity() instanceof Player player && !player.level().isClientSide) {
            // 确认喝的是水瓶
            if (event.getItem().is(Items.POTION) && PotionUtils.getPotion(event.getItem()) == Potions.WATER) {
                // 如果玩家有“健胃消食”效果，则移除它
                if (player.hasEffect(ModEffects.DIGESTION_AID.get())) {
                    player.removeEffect(ModEffects.DIGESTION_AID.get());
                }
            }
        }
    }
}
