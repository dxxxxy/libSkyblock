package studio.dreamys.libSkyblock.util;

import net.minecraft.client.Minecraft;
import net.minecraft.scoreboard.ScoreObjective;

import java.util.List;

public class ServerUtils {
    /**
     * Checks if the player is on Hypixel.
     * */
    public static boolean isOnHypixel() {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc != null && mc.theWorld != null && !mc.isSingleplayer()) {
            return mc.getCurrentServerData().serverIP.toLowerCase().contains("hypixel");
        }
        return false;
    }

    /**
     * Checks if the player is on Skyblock.
     * */
    public static boolean isOnSkyblock() {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc != null && mc.theWorld != null && !mc.isSingleplayer() && isOnHypixel()) {
            ScoreObjective scoreboardObj = mc.theWorld.getScoreboard().getObjectiveInDisplaySlot(1);
            if (scoreboardObj != null) {
                String scObjName = ScoreboardUtils.cleanSB(scoreboardObj.getDisplayName());
                return scObjName.contains("SKYBLOCK");
            }
        }
        return false;
    }

    /**
     * Checks if the player is in Dungeons.
     * */
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

    public String getServerName() {

        return null;
    }
}
