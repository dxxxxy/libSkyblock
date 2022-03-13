package studio.dreamys.libSkyblock.item;

import javafx.util.Pair;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import org.apache.commons.lang3.text.WordUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SBItem {
    private ItemStack stack;

    private final int NBT_INTEGER = 3;
    private final int NBT_STRING = 8;
    private final int NBT_LIST = 9;

    private final Pattern ITEM_PATTERN = Pattern.compile("§l(?<rarity>[A-Z]+) ?(?<type>[A-Z ]+)?(?:§[0-9a-f]§l§ka)?$");

    public SBItem(ItemStack stack) {
        this.stack = stack;
    }

    public ItemRarity getRarity() {
        if (stack.hasTagCompound()) {
            for (int i = getLore().size() - 1; i >= 0; i--) {
                String currentLine = getLore().get(i);

                Matcher rarityMatcher = ITEM_PATTERN.matcher(currentLine);
                if (rarityMatcher.find()) {
                    String rarity = rarityMatcher.group("rarity");

                    for (ItemRarity itemRarity : ItemRarity.values()) {
                        // Use a "startsWith" check here because "VERY SPECIAL" has two words and only "VERY" is matched.
                        if (itemRarity.name().replaceAll("_", " ").startsWith(rarity)) {
                            return itemRarity;
                        }
                    }
                }
            }
        }
        return null;
    }

    //currently does not work
    public ItemType getType() {
        // Start from the end since the rarity is usually the last line or one of the last.
        if (stack.hasTagCompound()) {
            for (int i = getLore().size() - 1; i >= 0; i--) {
                String currentLine = getLore().get(i);
                Matcher itemTypeMatcher = ITEM_PATTERN.matcher(currentLine);
                if (itemTypeMatcher.find()) {
                    String type = itemTypeMatcher.group("type");
                    System.out.println("found");
                    if (type != null) {
                        for (ItemType itemType : ItemType.values()) {
                            if (itemType.name().replaceAll("_", " ").startsWith(type)) {
                                return itemType;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public String getReforge() {
        if (stack.hasTagCompound()) {
            NBTTagCompound tag = stack.getTagCompound();
            if (tag.hasKey("ExtraAttributes")) {
                tag = tag.getCompoundTag("ExtraAttributes");
                if (tag.hasKey("modifier")) {
                    return WordUtils.capitalizeFully(tag.getString("modifier"));
                }
            }
        }
        return null;
    }

    public List<String> getLore() {
        if (stack.hasTagCompound()) {
            NBTTagCompound display = stack.getSubCompound("display", false);

            if (display != null && display.hasKey("Lore", NBT_LIST)) {
                NBTTagList lore = display.getTagList("Lore", NBT_STRING);

                List<String> loreAsList = new ArrayList<>();
                for (int lineNumber = 0; lineNumber < lore.tagCount(); lineNumber++) {
                    loreAsList.add(lore.getStringTagAt(lineNumber));
                }

                return loreAsList;
            }
        }
        return null;
    }

    //returns a Pair<String, Integer> where String is the name and Integer is the tier (or level)
    public Pair<String, Integer> getRune() {
        if (stack.hasTagCompound()) {
            NBTTagCompound tag = stack.getTagCompound();
            if (tag.hasKey("ExtraAttributes")) {
                tag = tag.getCompoundTag("ExtraAttributes");
                if (tag.hasKey("runes")) {
                    tag = tag.getCompoundTag("runes");

                    //in case no runes
                    String type = "none";
                    int tier = 0;

                    for (String rune : tag.getKeySet()) {
                        type = rune;
                        tier = tag.getInteger(rune);
                    }

                    return new Pair<>(WordUtils.capitalizeFully(type.replaceAll("_", " ")), tier);
                }
            }
        }
        return null;
    }

    //certain items like livid fragments can increase the cap by 1, so with a recombobulator, you could have 2
    public int getRarityUpgradeCount() {
        if (stack.hasTagCompound()) {
            NBTTagCompound tag = stack.getTagCompound();
            if (tag.hasKey("ExtraAttributes")) {
                tag = tag.getCompoundTag("ExtraAttributes");
                if (tag.hasKey("rarity_upgrades")) {
                    return tag.getInteger("rarity_upgrades");
                }
            }
        }
        return 0;
    }

    //includes master stars
    public int getDungeonStarsCount() {
        if (stack.hasTagCompound()) {
            NBTTagCompound tag = stack.getTagCompound();
            if (tag.hasKey("ExtraAttributes")) {
                tag = tag.getCompoundTag("ExtraAttributes");
                if (tag.hasKey("dungeon_item_level")) {
                    return tag.getInteger("dungeon_item_level");
                }
            }
        }
        return 0;
    }

    //string id of an item
    public String getID() {
        if (stack.hasTagCompound()) {
            NBTTagCompound tag = stack.getTagCompound();
            if (tag.hasKey("ExtraAttributes")) {
                tag = tag.getCompoundTag("ExtraAttributes");
                if (tag.hasKey("id")) {
                    return tag.getString("id");
                }
            }
        }
        return null;
    }

    //uuid of an item
    public String getUUID() {
        if (stack.hasTagCompound()) {
            NBTTagCompound tag = stack.getTagCompound();
            if (tag.hasKey("ExtraAttributes")) {
                tag = tag.getCompoundTag("ExtraAttributes");
                if (tag.hasKey("uuid")) {
                    return tag.getString("uuid");
                }
            }
        }
        return null;
    }

    public boolean isDonatedMuseum() {
        if (stack.hasTagCompound()) {
            NBTTagCompound tag = stack.getTagCompound();
            if (tag.hasKey("ExtraAttributes")) {
                tag = tag.getCompoundTag("ExtraAttributes");
                return tag.hasKey("donated_museum");
            }
        }
        return false;
    }

    //kinda where it came from
    public String getOriginTag() {
        if (stack.hasTagCompound()) {
            NBTTagCompound tag = stack.getTagCompound();
            if (tag.hasKey("ExtraAttributes")) {
                tag = tag.getCompoundTag("ExtraAttributes");
                if (tag.hasKey("originTag")) {
                    return tag.getString("originTag");
                }
            }
        }
        return null;
    }

    //kinda where it came from
    public String getTimestamp() {
        if (stack.hasTagCompound()) {
            NBTTagCompound tag = stack.getTagCompound();
            if (tag.hasKey("ExtraAttributes")) {
                tag = tag.getCompoundTag("ExtraAttributes");
                if (tag.hasKey("timestamp")) {
                    return tag.getString("timestamp");
                }
            }
        }
        return null;
    }

    //returns a Hashmap<String, Integer> where String is the name and Integer is the tier (or level)
    public HashMap<String, Integer> getEnchantments() {
        if (stack.hasTagCompound()) {
            NBTTagCompound tag = stack.getTagCompound();
            if (tag.hasKey("ExtraAttributes")) {
                tag = tag.getCompoundTag("ExtraAttributes");
                if (tag.hasKey("enchantments")) {
                    tag = tag.getCompoundTag("enchantments");
                    HashMap<String, Integer> enchantments = new HashMap<>();

                    for (String enchantment : tag.getKeySet()) {
                        enchantments.put(WordUtils.capitalizeFully(enchantment.replaceAll("_", " ")), tag.getInteger(enchantment));
                    }

                    return enchantments;
                }
            }
        }
        return null;
    }

    //includes fuming potato books
    public int getHotPotatoBookCount() {
        if (stack.hasTagCompound()) {
            NBTTagCompound tag = stack.getTagCompound();
            if (tag.hasKey("ExtraAttributes")) {
                tag = tag.getCompoundTag("ExtraAttributes");
                if (tag.hasKey("hot_potato_count")) {
                    return tag.getInteger("hot_potato_count");
                }
            }
        }
        return 0;
    }

    public ItemStack getStack() {
        return stack;
    }
}
