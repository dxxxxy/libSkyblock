package studio.dreamys.libSkyblock.player;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.entity.player.EntityPlayer;
import studio.dreamys.libSkyblock.item.SBItem;
import studio.dreamys.libSkyblock.util.ActionBarUtils;
import studio.dreamys.libSkyblock.util.ScoreboardUtils;
import studio.dreamys.libSkyblock.util.TabUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A utility class meant for dealing with the local skyblock player.
 * */
public class SBPlayer {
    private final EntityPlayer player;

    private final SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");
    private final SimpleDateFormat formatter2 = new SimpleDateFormat("dd hh mm ss");

    public SBPlayer(EntityPlayer player) {
        this.player = player;
    }

    /**
     * Gets the skyblock health of the player as an {@link Integer}.
     * */
    public int getHealth() {
        String bar = ChatFormatting.stripFormatting(ActionBarUtils.getActionBar());
        if (bar != null) {
            Matcher m = Pattern.compile("(\\d+)/\\d+\u2764").matcher(bar);
            if (m.find()) {
                return Integer.parseInt(m.group(1));
            }
        }
        return 0;
    }

    /**
     * Gets the skyblock max health of the player as an {@link Integer}.
     * */
    public int getMaxHealth() {
        String bar = ChatFormatting.stripFormatting(ActionBarUtils.getActionBar());
        if (bar != null) {
            Matcher m = Pattern.compile("(\\d+)\u2764").matcher(bar);
            if (m.find()) {
                return Integer.parseInt(m.group(1));
            }
        }
        return 0;
    }

    /**
     * Gets the skyblock mana of the player as an {@link Integer}.
     * */
    public int getMana() {
        String bar = ChatFormatting.stripFormatting(ActionBarUtils.getActionBar());
        if (bar != null) {
            Matcher m = Pattern.compile("(\\d+)/\\d+\u270E").matcher(bar);
            if (m.find()) {
                return Integer.parseInt(m.group(1));
            }
        }
        return 0;
    }

    /**
     * Gets the skyblock max mana of the player as an {@link Integer}.
     * */
    public int getMaxMana() {
        String bar = ChatFormatting.stripFormatting(ActionBarUtils.getActionBar());
        if (bar != null) {
            Matcher m = Pattern.compile("(\\d+)\u270E").matcher(bar);
            if (m.find()) {
                return Integer.parseInt(m.group(1));
            }
        }
        return 0;
    }

    /**
     * Gets the skyblock defense of the player as an {@link Integer}.
     * */
    public int getDefense() {
        String bar = ChatFormatting.stripFormatting(ActionBarUtils.getActionBar());
        if (bar != null) {
            Matcher m = Pattern.compile("(\\d+)\u2748").matcher(bar);
            if (m.find()) {
                return Integer.parseInt(m.group(1));
            }
        }
        return 0;
    }

    /**
     * Gets the skyblock effective health of the player as a {@link Double}.
     * */
    public double getEffectiveHealth() {
        return getHealth() * (1 + (getDefense() / 100.0));
    }

    /**
     * Gets the skyblock effective health of the player as a {@link Double}.
     * */
    public double getEffectiveMaxHealth() {
        return getMaxHealth() * (1 + (getDefense() / 100.0));
    }

    /**
     * Gets the skyblock speed of the player as an {@link Integer}.
     * */
    public int getSpeed() {
        String entry = TabUtils.findEntry("Speed:");
        if (entry != null) {
            Matcher m = Pattern.compile("\u2726(.*)").matcher(entry);
            if (m.find()) {
                return Integer.parseInt(m.group(1));
            }
        }
        return 0;
    }

    /**
     * Gets the skyblock strength of the player as an {@link Integer}.
     * */
    public int getStrength() {
        String entry = TabUtils.findEntry("Strength:");
        if (entry != null) {
            Matcher m = Pattern.compile("\u2741(.*)").matcher(entry);
            if (m.find()) {
                return Integer.parseInt(m.group(1));
            }
        }
        return 0;
    }

    /**
     * Gets the skyblock crit chance of the player as an {@link Integer}.
     * */
    public int getCritChance() {
        String entry = TabUtils.findEntry("Crit Chance:");
        if (entry != null) {
            Matcher m = Pattern.compile("\u2623(.*)").matcher(entry);
            if (m.find()) {
                return Integer.parseInt(m.group(1));
            }
        }
        return 0;
    }

    /**
     * Gets the skyblock crit damage of the player as an {@link Integer}.
     * */
    public int getCritDamage() {
        String entry = TabUtils.findEntry("Crit Damage:");
        if (entry != null) {
            Matcher m = Pattern.compile("\u2620(.*)").matcher(entry);
            if (m.find()) {
                return Integer.parseInt(m.group(1));
            }
        }
        return 0;
    }

    /**
     * Gets the skyblock attack speed of the player as an {@link Integer}.
     * */
    public int getAttackSpeed() {
        String entry = TabUtils.findEntry("Attack Speed:");
        if (entry != null) {
            Matcher m = Pattern.compile("\u2694(.*)").matcher(entry);
            if (m.find()) {
                return Integer.parseInt(m.group(1));
            }
        }
        return 0;
    }

    /**
     * Gets the skyblock gems of the player as an {@link Integer}.
     * */
    public int getGems() {
        String entry = TabUtils.findEntry("Gems:");
        if (entry != null) {
            Matcher m = Pattern.compile("(\\d+)").matcher(entry);
            if (m.find()) {
                return Integer.parseInt(m.group(1));
            }
        }
        return 0;
    }

    /**
     * Checks if the player has a piggy bank
     * */
    public boolean hasPiggy() {
        for (String score : ScoreboardUtils.getSidebarLines()) {
            String target = ScoreboardUtils.cleanSB(score);
            Matcher m = Pattern.compile("Piggy").matcher(target);
            return m.find();
        }
        return false;
    }

    /**
     * Checks if a cookie buff is active.
     * */
    public boolean isCookieActive() {
        String footer = TabUtils.getFooter();
        if (footer != null) {
            Matcher m = Pattern.compile("Cookie Buff").matcher(footer);
            return m.find();
        }
        return false;
    }

    /**
     * Gets the time of the cookie buff as a {@link Long}.
     * */
    public long getCookieMillis() {
        String footer = TabUtils.getFooter();
        if (footer != null) {
            Matcher m = Pattern.compile("Cookie Buff\\n(.*)").matcher(footer);
            if (m.find()) {
                try {
                    return formatter2.parse(m.group(1).replaceAll("[a-z]", "")).getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        return 0;
    }

    /**
     * Checks if a god potion is active.
     * */
    public boolean isGodpotionActive() {
        String footer = TabUtils.getFooter();
        if (footer != null) {
            Matcher m = Pattern.compile("You have a God Potion active!").matcher(footer);
            return m.find();
        }
        return false;
    }

    /**
     * Gets the time of the god potion as a {@link Long}.
     * */
    public long getGodpotionMillis() {
        String footer = TabUtils.getFooter();
        if (footer != null) {
            Matcher m = Pattern.compile("You have a God Potion active! (.*)").matcher(footer);
            if (m.find()) {
                try {
                    return formatter.parse(m.group(1)).getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        return 0;
    }

    /**
     * Gets the skyblock purse of the player as a {@link Double}.
     * */
    public double getPurse() {
        for (String score : ScoreboardUtils.getSidebarLines()) {
            String target = ScoreboardUtils.cleanSB(score);

            //Sometimes, you would get (+27) which would break it.
            Matcher m = Pattern.compile("(?:Purse|Piggy): (.*) \\(\\+\\d+\\)").matcher(target);
            if (m.find()) {
                return Double.parseDouble(m.group(1).replaceAll(",", ""));
            } else {
                m = Pattern.compile("(?:Purse|Piggy): (.*)").matcher(target);
                if (m.find()) {
                    return Double.parseDouble(m.group(1).replaceAll(",", ""));
                }
            }
        }
        return 0;
    }

    /**
     * Gets the skyblock bits of the player as an {@link Integer}.
     * */
    public int getBits() {
        for (String score : ScoreboardUtils.getSidebarLines()) {
            String target = ScoreboardUtils.cleanSB(score);

            //Sometimes, you would get (+350) which would break it.
            Matcher m = Pattern.compile("Bits: (.*)\\(\\+\\d+\\)").matcher(target);
            if (m.find()) {
                return Integer.parseInt(m.group(1).replaceAll(",", ""));
            } else {
                m = Pattern.compile("Bits: (.*)").matcher(target);
                if (m.find()) {
                    return Integer.parseInt(m.group(1).replaceAll(",", ""));
                }
            }
        }
        return 0;
    }

    /**
     * TODO
     * */
    public double getBank() {

        return 0;
    }

    /**
     * Gets the skyblock island of the player as a {@link String}.
     * */
    public String getIsland() {
        String entry = TabUtils.findEntry("Area:");
        if (entry != null) {
            Matcher m = Pattern.compile("Area: (.*)").matcher(entry);
            if (m.find()) {
                return m.group(1);
            }
        }
        return null;
    }

    /**
     * TODO
     * */
    public String getIslandLocation() {

        return null;
    }

    /**
     * Gets the player's current held item as a {@link SBItem}
     * */
    public SBItem getHeldItem() {
        return new SBItem(player.getHeldItem());
    }

    /**
     * Gets the player's equipped helmet as a {@link SBItem}
     * */
    public SBItem getHelmet() {
        return new SBItem(player.getCurrentArmor(3));
    }

    /**
     * Gets the player's equipped chestplate as a {@link SBItem}
     * */
    public SBItem getChestplate() {
        return new SBItem(player.getCurrentArmor(2));
    }

    /**
     * Gets the player's equipped leggings as a {@link SBItem}
     * */
    public SBItem getLeggings() {
        return new SBItem(player.getCurrentArmor(1));
    }

    /**
     * Gets the player's equipped boots as a {@link SBItem}
     * */
    public SBItem getBoots() {
        return new SBItem(player.getCurrentArmor(0));
    }

    /**
     * Gets the original EntityPlayer that has been passed to this object.
     * */
    public EntityPlayer getPlayer() {
        return player;
    }
}
