package studio.dreamys.test;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import studio.dreamys.libSkyblock.item.SBItem;
import studio.dreamys.libSkyblock.util.ServerUtils;

import java.awt.*;

@Mod(modid = TestMod.MODID, name = TestMod.NAME, version = TestMod.VERSION)
public class TestMod {
    public static final String MODID = "test";
    public static final String NAME = "test";
    public static final String VERSION = "1.0";

    public static int y;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent e) {
        if (e.type == RenderGameOverlayEvent.ElementType.TEXT) {
            y = 0;
            ItemStack currentlyHeld = Minecraft.getMinecraft().thePlayer.getHeldItem();
            if (currentlyHeld == null || !ServerUtils.isOnSkyblock()) return;
            SBItem item = new SBItem(currentlyHeld);
            if (item.getRarity() != null) drawText("getRarity: " + item.getRarity().getName());
            if (item.getType() != null) drawText("getType: " + item.getType().getName());
            if (item.getReforge() != null) drawText("getReforge: " + item.getReforge());
//            drawText(item.getLore().toString());
            if (item.getRune() != null) drawText("getRune: " + item.getRune().getKey() + ": " + item.getRune().getValue());
            drawText("getRarityUpgradeCount: " + item.getRarityUpgradeCount() + "");
            drawText("getDungeonStarsCount: " + item.getDungeonStarsCount() + "");
            if (item.getID() != null) drawText("getID: " + item.getID());
            if (item.getUUID() != null) drawText("getUUID: " + item.getUUID());
            drawText("isDonatedMuseum: " + item.isDonatedMuseum() + "");
            if (item.getOriginTag() != null) drawText("getOriginTag: " + item.getOriginTag());
            if (item.getTimestamp() != null) drawText("getTimestamp: " + item.getTimestamp());
//            drawText(item.getEnchantments().toString());
            drawText("getHotPotatoBookCount: " + item.getHotPotatoBookCount() + "");
        }
    }

    public void drawText(String text) {
        FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
        fr.drawString(text, 0, y, Color.WHITE.getRGB());
        y += fr.FONT_HEIGHT;
    }
}