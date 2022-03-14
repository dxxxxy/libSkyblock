package studio.dreamys.libSkyblock.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class ActionBarUtils {
    public static String getActionBar() {
        return ReflectionHelper.getPrivateValue(GuiIngame.class, Minecraft.getMinecraft().ingameGUI, "recordPlaying", "field_73838_g");
    }
}
