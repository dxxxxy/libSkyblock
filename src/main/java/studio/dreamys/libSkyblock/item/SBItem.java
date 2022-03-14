package studio.dreamys.libSkyblock.item;

import com.mojang.realmsclient.gui.ChatFormatting;
import javafx.util.Pair;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import studio.dreamys.libSkyblock.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A utility class meant for dealing with skyblock items.
 * */
public class SBItem {
    private final ItemStack stack;

    private final int NBT_INTEGER = 3;
    private final int NBT_STRING = 8;
    private final int NBT_LIST = 9;

    private final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy hh:mm a");
    private final SimpleDateFormat formatter2 = new SimpleDateFormat("MM/dd/yy hh:mm a");

    public SBItem(ItemStack stack) {
        this.stack = stack;
    }

    /**
     * Gets the rarity of the item as an {@link ItemRarity}.
     */
    public ItemRarity getRarity() {
        if (stack.hasTagCompound()) {
            for (int i = getLore().size() - 1; i >= 0; i--) {
                String currentLine = getLore().get(i);

                Matcher rarityMatcher = Pattern.compile("§l(\\w+)").matcher(currentLine);
                if (rarityMatcher.find()) {
                    String rarity = rarityMatcher.group(0);

                    for (ItemRarity itemRarity : ItemRarity.values()) {
                        //using startsWith because only VERY is matched in VERY SPECIAL
                        if (itemRarity.name().replaceAll("_", " ").startsWith(rarity)) {
                            return itemRarity;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Gets the reforge of the item as a pretty {@link String}.
     */
    public String getReforge() {
        if (stack.hasTagCompound()) {
            NBTTagCompound tag = stack.getTagCompound();
            if (tag.hasKey("ExtraAttributes")) {
                tag = tag.getCompoundTag("ExtraAttributes");
                if (tag.hasKey("modifier")) {
                    //weird odd_sword reforge name
                    return StringUtils.prettify(tag.getString("modifier").replaceAll("_sword", ""));
                }
            }
        }
        return null;
    }

    /**
     * Checks if the item is a material meant to be used in a crafting recipe.
     */
    public boolean isMaterialForRecipe() {
        List<String> lore = getLore();
        for (String loreLine : lore) {
            if ("Right-click to view recipes!".equals(ChatFormatting.stripFormatting(loreLine))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the ability scrolls of the item as a pretty {@link List<String>}.
     */
    public List<String> getAbilityScrolls() {
        if (stack.hasTagCompound()) {
            NBTTagCompound tag = stack.getTagCompound();
            if (tag.hasKey("ExtraAttributes")) {
                tag = tag.getCompoundTag("ExtraAttributes");
                if (tag.hasKey("ability_scroll", NBT_LIST)) {
                    NBTTagList scrolls = tag.getTagList("ability_scroll", NBT_STRING);

                    List<String> scrollsList = new ArrayList<>();
                    for (int lineNumber = 0; lineNumber < scrolls.tagCount(); lineNumber++) {
                        scrollsList.add(StringUtils.prettify(scrolls.getStringTagAt(lineNumber)));
                    }

                    return scrollsList;
                }
            }
        }
        return null;
    }

    /**
     * Gets the gems of the item as a {@link List<Pair>}.
     * <br>
     * {@link List<Pair>}{@code <Pair<K>>} stores the name of the gem slot as a {@link String}.
     * <br>
     * {@link List<Pair>}{@code <Pair<V>>} stores the tier of the gem as a {@link String}.<br><br>
     * Note: ambiguous gem slots are written differently.<br>
     * Example:<br>
     * "gems": {<br>
     * &nbsp;&nbsp;&nbsp;&nbsp;"JASPER_0": "FINE",<br>
     * &nbsp;&nbsp;&nbsp;&nbsp;"COMBAT_0": "FINE",<br>
     * &nbsp;&nbsp;&nbsp;&nbsp;"COMBAT_0_gem": "JASPER"<br>
     * }<br><br>
     * Here we can see that the COMBAT_0 first tells us what tier it is, and then what gem type it actually is.
     * However, this method fixes that and only returns the names and tiers of the runes. This is done by waiting for the next occurence
     * of a gem entry and matching it to the previous using temporary storage.
     */
    public List<Pair<String, String>> getGems() {
        if (stack.hasTagCompound()) {
            NBTTagCompound tag = stack.getTagCompound();
            if (tag.hasKey("ExtraAttributes")) {
                tag = tag.getCompoundTag("ExtraAttributes");
                if (tag.hasKey("gems")) {
                    tag = tag.getCompoundTag("gems");
                    List<Pair<String, String>> gems = new ArrayList<>();
                    Pair<String, String> temp = new Pair<>("null", "null");
                    for (String gem : tag.getKeySet()) {
                        //normally, ambiguous gems are right after each other
                        if (!Pattern.compile("RUBY|AMBER|SAPPHIRE|JADE|AMETHYST|TOPAZ|JASPER").matcher(gem).find()) {
                            //scans COMBAT_0_gem, JASPER
                            if (gem.startsWith(temp.getKey()) && gem.endsWith("_gem")) {
                                //stores JASPER, FINE
                                gems.add(new Pair<>(StringUtils.prettify(tag.getString(gem)), StringUtils.prettify(temp.getValue())));
                                temp = new Pair<>("null", "null");
                                continue;
                            }
                            //stores COMBAT_0, FINE
                            temp = new Pair<>(gem, tag.getString(gem));
                            continue;
                        }
                        gems.add(new Pair<>(StringUtils.prettify(gem.replaceAll("_(.*)", "")), StringUtils.prettify(tag.getString(gem))));
                    }
                    return gems;
                }
            }
        }
        return null;
    }

    /**
     * Gets the lore of the item as a {@link List<String>}.
     */
    public List<String> getLore() {
        if (stack.hasTagCompound()) {
            NBTTagCompound display = stack.getSubCompound("display", false);

            if (display != null && display.hasKey("Lore", NBT_LIST)) {
                NBTTagList lore = display.getTagList("Lore", NBT_STRING);

                List<String> loreList = new ArrayList<>();
                for (int lineNumber = 0; lineNumber < lore.tagCount(); lineNumber++) {
                    loreList.add(lore.getStringTagAt(lineNumber));
                }

                return loreList;
            }
        }
        return null;
    }

    /**
     * Sets the lore of the item.
     */
    public void setLore(List<String> lore) {
        NBTTagCompound display = stack.getSubCompound("display", true);

        NBTTagList loreTagList = new NBTTagList();
        for (String loreLine : lore) {
            loreTagList.appendTag(new NBTTagString(loreLine));
        }

        display.setTag("Lore", loreTagList);
    }

    /**
     * Gets the rune of the item as a {@link Pair}
     * <br>
     * {@link Pair#getKey()} returns the name of the rune as a {@code String}.
     * <br>
     * {@link Pair#getValue()} returns the tier/level of the rune as an {@code int}.
     */
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

                    return new Pair<>(StringUtils.prettify(type), tier);
                }
            }
        }
        return null;
    }

    /**
     * Gets the number of rarity upgrades of the item.
     * Certain items like livid fragments can increase the cap by 1, so with a recombobulator, you could have 2 rarity upgrades.
     */
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

    /**
     * Gets the number of dungeon stars of the item.
     * Includes master stars.
     */
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

    /**
     * Gets the id/internal name of the item (ex: WITHER_CHESTPLATE).
     */
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

    /**
     * Gets the uuid of the item (unique id).
     */
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

    /**
     * Checks if the item has been donated to the museum.
     */
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

    /**
     * Gets the origin tag of the item.
     * Kind of where it came from (ex: CRAFTING_GRID_COLLECT).
     */
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

    /**
     * Gets the timestamp of the item as a {@link String}.
     */
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

    /**
     * Gets the timestamp of the item as a {@link Date}.
     */
    public Date getTimestamp2() {
        if (stack.hasTagCompound()) {
            NBTTagCompound tag = stack.getTagCompound();
            if (tag.hasKey("ExtraAttributes")) {
                tag = tag.getCompoundTag("ExtraAttributes");
                if (tag.hasKey("timestamp")) {
                    try {
                        Date date = formatter.parse(tag.getString("timestamp"));

                        //Hypixel has inverse timestamps on some items which results in the items being parsed in the future
                        if (date.getTime() >= new Date().getTime()) {
                            date = formatter2.parse(tag.getString("timestamp"));
                        }

                        return date;
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    /**
     * Gets the enchantments of the item as a {@link HashMap}.
     * <br>
     * {@link HashMap}{@code <K>} stores the name of the enchantment as a pretty {@link String}.
     * <br>
     * {@link HashMap}{@code <V>} stores the tier/level of the enchantment as an {@link Integer}.
     */
    public HashMap<String, Integer> getEnchantments() {
        if (stack.hasTagCompound()) {
            NBTTagCompound tag = stack.getTagCompound();
            if (tag.hasKey("ExtraAttributes")) {
                tag = tag.getCompoundTag("ExtraAttributes");
                if (tag.hasKey("enchantments")) {
                    tag = tag.getCompoundTag("enchantments");
                    HashMap<String, Integer> enchantments = new HashMap<>();

                    for (String enchantment : tag.getKeySet()) {
                        enchantments.put(StringUtils.prettify(enchantment), tag.getInteger(enchantment));
                    }

                    return enchantments;
                }
            }
        }
        return null;
    }

    /**
     * Gets the number of hot potato books of the item. Includes fuming books.
     * */
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

    /**
     * Checks if the item is a Drill.
     */
    public boolean isDrill() {
        if (stack.hasTagCompound()) {
            NBTTagCompound tag = stack.getTagCompound();
            if (tag.hasKey("ExtraAttributes")) {
                tag = tag.getCompoundTag("ExtraAttributes");
                return tag.hasKey("drill_fuel");
            }
        }
        return false;
    }

    /**
     * Get the original ItemStack that has been passed to this object.
     * */
    public ItemStack getStack() {
        return stack;
    }
}
