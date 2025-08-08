package com.fred.rgbchat;

import com.fred.rgbchat.truergb.TrueRGBSimpleRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;


@Mod(modid = Tags.MOD_ID, name = Tags.MOD_NAME, version = Tags.VERSION)
public class RGBChat
{
    private TrueRGBSimpleRenderer renderer;

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        this.renderer = new TrueRGBSimpleRenderer(mc.gameSettings, new ResourceLocation("textures/font/ascii.png"), mc.renderEngine, false);
        ((SimpleReloadableResourceManager)mc.getResourceManager()).registerReloadListener((IResourceManagerReloadListener)this.renderer);

    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        mc.fontRenderer = this.renderer;
        if (mc.gameSettings.language != null) {
            this.renderer.setUnicodeFlag(mc.isUnicode());
            this.renderer.setBidiFlag(mc.getLanguageManager().isCurrentLanguageBidirectional());
        }
    }

}
