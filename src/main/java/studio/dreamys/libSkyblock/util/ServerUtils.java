package studio.dreamys.libSkyblock.util;

import net.minecraft.client.Minecraft;
import net.minecraft.scoreboard.ScoreObjective;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public static boolean isInDungeons() {
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

    /**
     * Gets the name of the server (ex: m677A)
     * */
    public static String getServerName() {
        for (String score : ScoreboardUtils.getSidebarLines()) {
            String target = ScoreboardUtils.cleanSB(score);
            Matcher m = Pattern.compile("\\d{2}/\\d{2}/\\d{2} (.*)").matcher(target);
            if (m.find()) {
                return m.group(1);
            }
        }
        return null;
    }
}
