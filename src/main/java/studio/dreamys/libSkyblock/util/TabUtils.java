package studio.dreamys.libSkyblock.util;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.WorldSettings;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class TabUtils {
    private static final Ordering<NetworkPlayerInfo> playerOrderer = Ordering.from(new PlayerComparator());

    public static String findEntry(String text) {
        Collection<NetworkPlayerInfo> playerInfoMap = Minecraft.getMinecraft().thePlayer.sendQueue.getPlayerInfoMap();
        List<NetworkPlayerInfo> networkPlayerInfos = playerOrderer.sortedCopy(playerInfoMap);
        GuiPlayerTabOverlay tabList = Minecraft.getMinecraft().ingameGUI.getTabList();
        for (NetworkPlayerInfo playerInfo : networkPlayerInfos) {
            if (playerInfo.getGameProfile().getName().startsWith("!")) {
                String tabListEntry = ChatFormatting.stripFormatting(tabList.getPlayerName(playerInfo));
                if (tabListEntry != null && tabListEntry.contains(text)) {
                    return tabListEntry;
                }
            }
        }
        return null;
    }

    public static String getFooter() {
        IChatComponent footer = ReflectionHelper.getPrivateValue(GuiPlayerTabOverlay.class, Minecraft.getMinecraft().ingameGUI.getTabList(), "footer", "field_175255_h");
        return footer.getUnformattedText();
    }

    static class PlayerComparator implements Comparator<NetworkPlayerInfo> {
        public int compare(NetworkPlayerInfo playerInfo1, NetworkPlayerInfo playerInfo2) {
            ScorePlayerTeam scorePlayerTeam1 = playerInfo1.getPlayerTeam();
            ScorePlayerTeam scorePlayerTeam2 = playerInfo2.getPlayerTeam();
            return ComparisonChain.start().compareTrueFirst(playerInfo1.getGameType() != WorldSettings.GameType.SPECTATOR, playerInfo2.getGameType() != WorldSettings.GameType.SPECTATOR).compare(scorePlayerTeam1 != null ? scorePlayerTeam1.getRegisteredName() : "", scorePlayerTeam2 != null ? scorePlayerTeam2.getRegisteredName() : "").compare(playerInfo1.getGameProfile().getName(), playerInfo2.getGameProfile().getName()).result();
        }
    }
}
