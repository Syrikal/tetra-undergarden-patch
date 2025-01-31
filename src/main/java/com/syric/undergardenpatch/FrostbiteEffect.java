package com.syric.undergardenpatch;


import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import se.mickelus.tetra.blocks.workbench.gui.WorkbenchStatsGui;
import se.mickelus.tetra.effect.ItemEffect;
import se.mickelus.tetra.gui.stats.bar.GuiStatBar;
import se.mickelus.tetra.gui.stats.getter.IStatGetter;
import se.mickelus.tetra.gui.stats.getter.LabelGetterBasic;
import se.mickelus.tetra.gui.stats.getter.StatGetterEffectLevel;
import se.mickelus.tetra.gui.stats.getter.TooltipGetterNone;
import se.mickelus.tetra.items.modular.ModularItem;
import se.mickelus.tetra.items.modular.impl.holo.gui.craft.HoloStatsGui;

/**
 * Implementation of a slowing effect.
 */
public class FrostbiteEffect {
    public static final ItemEffect frostbite = ItemEffect.get("undergardenpatch:frostbite");

    /**
     * @param event
     * Event handler which checks if the mainhand item has our item effect
     */
    @SubscribeEvent
    public void attackEvent(LivingHurtEvent event) {
        Entity source = event.getSource().getEntity();

        if (source instanceof Player player) {
            ItemStack heldStack = player.getMainHandItem();

            if (heldStack.getItem() instanceof ModularItem item) {
                int level = item.getEffectLevel(heldStack, frostbite);

                if (level > 0) {
                    event.getEntity().addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 600, 2));
                    //player.sendMessage(new StringTextComponent("Applied Slowness 2"), player.getUUID());
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void addBars(FMLClientSetupEvent event) {
        IStatGetter frostbiteGetter = new StatGetterEffectLevel(frostbite, 1.0);
        GuiStatBar frostbiteBar = new GuiStatBar(0, 0, 59, "tetra.stats.frostbite", 0.0, 1.0, false, frostbiteGetter, LabelGetterBasic.noLabel, new TooltipGetterNone("tetra.stats.frostbite.tooltip"));

        WorkbenchStatsGui.addBar(frostbiteBar);
        HoloStatsGui.addBar(frostbiteBar);
    }

}
