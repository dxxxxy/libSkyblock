package studio.dreamys.libSkyblock.item;

import net.minecraft.util.EnumChatFormatting;
import org.apache.commons.lang3.text.WordUtils;

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

    public String getName() {
        return WordUtils.capitalizeFully(name().replaceAll("_", " "));
    }
}
