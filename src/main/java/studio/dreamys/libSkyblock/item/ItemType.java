package studio.dreamys.libSkyblock.item;

import org.apache.commons.lang3.text.WordUtils;

public enum ItemType {

    //tools and weapons
    AXE,
    BOW,
    DRILL,
    FISHING_ROD,
    FISHING_WEAPON,
    GAUNTLET,
    HOE,
    SHEARS,
    SHOVEL,
    SWORD,
    PICKAXE,
    WAND,

    //armor
    HELMET,
    CHESTPLATE,
    LEGGINGS,
    BOOTS,

    //other
    ACCESSORY,
    COSMETIC,
    DUNGEON_ITEM;

    public String getName() {
        return WordUtils.capitalizeFully(name().replaceAll("_", " "));
    }
}
