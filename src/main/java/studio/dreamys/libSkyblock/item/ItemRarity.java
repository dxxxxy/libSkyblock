package studio.dreamys.libSkyblock.item;

import net.minecraft.util.EnumChatFormatting;
import studio.dreamys.libSkyblock.util.StringUtils;

public enum ItemRarity {
    COMMON(EnumChatFormatting.WHITE),
    UNCOMMON(EnumChatFormatting.GREEN),
    RARE(EnumChatFormatting.BLUE),
    EPIC(EnumChatFormatting.DARK_PURPLE),
    LEGENDARY(EnumChatFormatting.GOLD),
    MYTHIC(EnumChatFormatting.LIGHT_PURPLE),
    DIVINE(EnumChatFormatting.AQUA),

    SPECIAL(EnumChatFormatting.RED),
    VERY_SPECIAL(EnumChatFormatting.RED);

    private EnumChatFormatting colorCode;

    ItemRarity(EnumChatFormatting colorCode) {
        this.colorCode = colorCode;
    }

    /**
     * Gets the name of the rarity as a pretty {@link String}.
     * */
    public String getName() {
        return StringUtils.prettify(name());
    }

    /**
     * Gets the color code of the rarity as an {@link EnumChatFormatting}.
     * */
    public EnumChatFormatting getColorCode() {
        return colorCode;
    }
}
