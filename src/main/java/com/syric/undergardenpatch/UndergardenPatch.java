package com.syric.undergardenpatch;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@Mod("undergardenpatch")
public class UndergardenPatch {
    public static final String MODID = "undergardenpatch";


    public UndergardenPatch() {
        MinecraftForge.EVENT_BUS.register(this);
//        MinecraftForge.EVENT_BUS.register(new BloodlustEffect());
        MinecraftForge.EVENT_BUS.register(new FrostbiteEffect());
        MinecraftForge.EVENT_BUS.register(new FrostnipEffect());
        MinecraftForge.EVENT_BUS.register(new RotbaneEffect());
        MinecraftForge.EVENT_BUS.register(new ThrenodyEffect());
        MinecraftForge.EVENT_BUS.register(new UndermineEffect());
    }
}
