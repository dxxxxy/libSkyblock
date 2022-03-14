package studio.dreamys.libSkyblock.player;

import net.minecraft.entity.player.EntityPlayer;
import studio.dreamys.libSkyblock.item.SBItem;

/**
 * A utility class meant for dealing with the local skyblock player.
 * */
public class SBPlayer {
    private final EntityPlayer player;

    public SBPlayer(EntityPlayer player) {
        this.player = player;
    }

    /**
     * Get the skyblock health of the player as an {@link Integer}.
     * */
    public int getHealth() {

        return 0;
    }

    /**
     * Get the skyblock max health of the player as an {@link Integer}.
     * */
    public int getMaxHealth() {

        return 0;
    }

    /**
     * Get the skyblock mana of the player as an {@link Integer}.
     * */
    public int getMana() {

        return 0;
    }

    /**
     * Get the skyblock max mana of the player as an {@link Integer}.
     * */
    public int getMaxMana() {

        return 0;
    }

    /**
     * Get the skyblock defense of the player as an {@link Integer}.
     * */
    public int getDefense() {

        return 0;
    }

    /**
     * Get the skyblock effective health of the player as a {@link Double}.
     * */
    public double getEffectiveHealth() {

        return 0;
    }

    /**
     * Get the skyblock speed of the player as an {@link Integer}.
     * */
    public int getSpeed() {

        return 0;
    }

    /**
     * Get the skyblock strength of the player as an {@link Integer}.
     * */
    public int getStrength() {

        return 0;
    }

    /**
     * Get the skyblock crit chance of the player as an {@link Integer}.
     * */
    public int getCritChance() {

        return 0;
    }

    /**
     * Get the skyblock crit damage of the player as an {@link Integer}.
     * */
    public int getCritDamage() {

        return 0;
    }

    /**
     * Get the skyblock attack speed of the player as an {@link Integer}.
     * */
    public int getAttackSpeed() {

        return 0;
    }

    /**
     * Get the skyblock gems of the player as an {@link Integer}.
     * */
    public int getGems() {

        return 0;
    }

    /**
     * Check if the player has a piggy bank
     * */
    public boolean hasPiggy() {

        return false;
    }

    /**
     * Check if a cookie buff is active.
     * */
    public boolean isCookieActive() {

        return false;
    }

    /**
     * Get the time of the cookie buff as a {@link Long}.
     * */
    public long cookieMillis() {

        return 0;
    }

    /**
     * Check if a god potion is active.
     * */
    public boolean isGodpotionActive() {

        return false;
    }

    /**
     * Get the time of the god potion as a {@link Long}.
     * */
    public long godpotionMillis() {

        return 0;
    }

    /**
     * Get the skyblock purse of the player as an {@link Integer}.
     * */
    public int getPurse() {

        return 0;
    }

    /**
     * Get the skyblock bank of the player as an {@link Integer}.
     * */
    public int getBank() {

        return 0;
    }

    public String getIsland() {

        return null;
    }

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
}
