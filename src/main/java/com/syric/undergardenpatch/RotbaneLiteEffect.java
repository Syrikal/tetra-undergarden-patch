package com.syric.undergardenpatch;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import quek.undergarden.registry.UGTags;
import se.mickelus.tetra.blocks.workbench.gui.WorkbenchStatsGui;
import se.mickelus.tetra.effect.ItemEffect;
import se.mickelus.tetra.gui.stats.bar.GuiStatBar;
import se.mickelus.tetra.gui.stats.getter.LabelGetterBasic;
import se.mickelus.tetra.gui.stats.getter.StatGetterEffectLevel;
import se.mickelus.tetra.gui.stats.getter.TooltipGetterNone;
import se.mickelus.tetra.items.modular.ModularItem;
import se.mickelus.tetra.items.modular.impl.holo.gui.craft.HoloStatsGui;


/**
 * Implementation of an effect which deals 1.4x damage to Rotspawn.
 */
public class RotbaneLiteEffect {
    private static final ItemEffect rotbane_lite = ItemEffect.get("undergardenpatch:rotbane_lite");

    /**
     * @param event
     * Event handler which checks if the mainhand item has our item effect
     */
    @SubscribeEvent
    public void attackEvent(LivingHurtEvent event) {
        Entity source = event.getSource().getEntity();
        float damage = event.getAmount();

        if (source instanceof Player player) {
            ItemStack heldStack = player.getMainHandItem();

            if (heldStack.getItem() instanceof ModularItem item) {
                int level = item.getEffectLevel(heldStack, rotbane_lite);
                boolean forgor = item.getEffectLevel(heldStack, ThrenodyEffect.threnody) > 0 || item.getEffectLevel(heldStack, ThrenodyLiteEffect.threnody_lite) > 0;
                boolean rotbane = item.getEffectLevel(heldStack, RotbaneEffect.rotbane) > 0;

                if (level > 0 && !forgor && !rotbane) {
                    if(event.getEntity().getType().is(UGTags.Entities.ROTSPAWN)) {
                        event.setAmount(damage * 1.25F);
//                        player.sendMessage(new StringTextComponent("150% damage to Rotspawn"), player.getUUID());
                        //event.getEntityLiving().setFire(1); //A very visible way to check if the effect is activating properly
                    }
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void addBars(FMLClientSetupEvent event) {
        var rotbaneGetter = new StatGetterEffectLevel(rotbane_lite, 1.0);
        GuiStatBar rotbaneBar = new GuiStatBar(0, 0, 59, "tetra.stats.rotbane_lite", 0.0, 1.0, false, rotbaneGetter, LabelGetterBasic.noLabel, new TooltipGetterNone("tetra.stats.rotbane_lite.tooltip"));

        WorkbenchStatsGui.addBar(rotbaneBar);
        HoloStatsGui.addBar(rotbaneBar);
    }
}
