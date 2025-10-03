package com.fred.rgbchat;

import com.fred.rgbchat.truergb.CachedRGBFontRenderer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;


@Mod(modid = Tags.MOD_ID, name = Tags.MOD_NAME, version = Tags.VERSION)
public class RGBChat {

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        CachedRGBFontRenderer.overrideFontRenderer();
    }

}
