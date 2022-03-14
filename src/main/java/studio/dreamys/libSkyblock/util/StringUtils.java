package studio.dreamys.libSkyblock.util;

import org.apache.commons.lang3.text.WordUtils;

public class StringUtils {
    public static String prettify(String text) {
        return WordUtils.capitalizeFully(text.replaceAll("_", " "));
    }
}
