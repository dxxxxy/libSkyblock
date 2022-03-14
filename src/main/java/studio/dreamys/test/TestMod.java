package studio.dreamys.test;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import studio.dreamys.libSkyblock.item.SBItem;
import studio.dreamys.libSkyblock.player.SBPlayer;
import studio.dreamys.libSkyblock.util.ServerUtils;

import java.awt.*;
import java.math.BigDecimal;

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
            ItemStack stack = Minecraft.getMinecraft().thePlayer.getHeldItem();
            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            if (stack == null || player == null || !ServerUtils.isOnSkyblock()) return;

            y = 0;
            SBItem i = new SBItem(stack);
            drawText("SBItem >"); //start
            if (i.getRarity() != null) drawText("getRarity: " + i.getRarity().getName());
            if (i.getReforge() != null) drawText("getReforge: " + i.getReforge());
            drawText("isMaterialForRecipe: " + i.isMaterialForRecipe());
            if (i.getLore() != null) drawText("getLore: " + i.getLore());
            if (i.getRune() != null) drawText("getRune: " + i.getRune().getKey() + ": " + i.getRune().getValue());
            drawText("getRarityUpgradeCount: " + i.getRarityUpgradeCount());
            drawText("getDungeonStarsCount: " + i.getDungeonStarsCount());
            if (i.getID() != null) drawText("getID: " + i.getID());
            if (i.getUUID() != null) drawText("getUUID: " + i.getUUID());
            if (i.getAbilityScrolls() != null) drawText("getAbilityScrolls: " + i.getAbilityScrolls());
            if (i.getGems() != null) drawText("getGems: " + i.getGems());
            drawText("isDonatedMuseum: " + i.isDonatedMuseum());
            if (i.getOriginTag() != null) drawText("getOriginTag: " + i.getOriginTag());
            if (i.getTimestamp() != null) drawText("getTimestamp: " + i.getTimestamp());
            if (i.getTimestamp2() != null) drawText("getTimestamp2: " + i.getTimestamp2());
            if (i.getEnchantments() != null) drawText("getEnchantments: " + i.getEnchantments());
            drawText("getHotPotatoBookCount: " + i.getHotPotatoBookCount());
            drawText("isDrill: " + i.isDrill());

            drawText(""); //enter

            SBPlayer p = new SBPlayer(player);
            drawText("SBPlayer >"); //start
            drawText("getHealth: " + p.getHealth());
            drawText("getMaxHealth: " + p.getMaxHealth());
            drawText("getMana: " + p.getMana());
            drawText("getMaxMana: " + p.getMaxMana());
            drawText("getDefense: " + p.getDefense());
            drawText("getEffectiveHealth: " + BigDecimal.valueOf(p.getEffectiveHealth()).toPlainString());
            drawText("getEffectiveMaxHealth: " + BigDecimal.valueOf(p.getEffectiveMaxHealth()).toPlainString());
            drawText("getSpeed: " + p.getSpeed());
            drawText("getStrength: " + p.getStrength());
            drawText("getCritChance: " + p.getCritChance());
            drawText("getCritDamage: " + p.getCritDamage());
            drawText("getAttackSpeed: " + p.getAttackSpeed());
            drawText("getGems: " + p.getGems());
            drawText("hasPiggy: " + p.hasPiggy());
            drawText("isCookieActive: " + p.isCookieActive());
            drawText("getCookieMillis: " + p.getCookieMillis());
            drawText("isGodpotionActive: " + p.isGodpotionActive());
            drawText("getGodpotionMillis: " + p.getGodpotionMillis());
            drawText("getPurse: " + BigDecimal.valueOf(p.getPurse()).toPlainString());
            drawText("getBits: " + p.getBits());
            drawText("getBank: " + BigDecimal.valueOf(p.getBank()).toPlainString());
            if (p.getIsland() != null) drawText("getIsland: " + p.getIsland());
            if (p.getIslandLocation() != null) drawText("getIslandLocation: " + p.getIslandLocation());
            if (p.getHeldItem() != null) drawText("getHeldItem: " + p.getHeldItem());
            if (p.getHelmet() != null) drawText("getHelmet: " + p.getHelmet());
            if (p.getChestplate() != null) drawText("getChestplate: " + p.getChestplate());
            if (p.getLeggings() != null) drawText("getLeggings: " + p.getLeggings());
            if (p.getBoots() != null) drawText("getBoots: " + p.getBoots());

            drawText(""); //enter

            drawText("ServerUtils >"); //start
            drawText("getServerName: " + ServerUtils.getServerName());
            drawText("isInDungeons: " + ServerUtils.isInDungeons());
            drawText("isOnSkyblock: " + ServerUtils.isOnSkyblock());
            drawText("isOnHypixel: " + ServerUtils.isOnHypixel());
        }
    }

    public void drawText(String text) {
        FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
        fr.drawString(text, 0, y, Color.WHITE.getRGB());
        y += fr.FONT_HEIGHT;
    }
}