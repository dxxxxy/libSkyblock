package studio.dreamys.libSkyblock.util;

import net.minecraft.client.Minecraft;
import net.minecraft.scoreboard.ScoreObjective;

import java.util.List;

public class ServerUtils {
    public static boolean isOnHypixel() {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc != null && mc.theWorld != null && !mc.isSingleplayer()) {
            return mc.getCurrentServerData().serverIP.toLowerCase().contains("hypixel");
        }
        return false;
    }

    public static boolean isOnSkyblock() {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc != null && mc.theWorld != null && !mc.isSingleplayer() && isOnHypixel()) {
            ScoreObjective scoreboardObj = mc.theWorld.getScoreboard().getObjectiveInDisplaySlot(1);
            if (scoreboardObj != null) {
                String scObjName = ScoreboardUtils.cleanSB(scoreboardObj.getDisplayName());
                if (scObjName.contains("SKYBLOCK")) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isOnDungeons() {
        if (isOnSkyblock()) {
            List<String> scoreboard = ScoreboardUtils.getSidebarLines();
            for (String s : scoreboard) {
                String sCleaned = ScoreboardUtils.cleanSB(s);
                if (sCleaned.contains("The Catacombs")) {
                    return true;
                }
            }
        }
        return false;
    }
}
