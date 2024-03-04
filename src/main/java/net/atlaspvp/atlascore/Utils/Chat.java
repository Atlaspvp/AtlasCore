package net.atlaspvp.atlascore.Utils;

import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Optional;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;

public class Chat {
    // Minimessage Related vvv

    private static final Pattern regexPlaceholderAPIPatern = Pattern.compile("%((?:(?!%%).)*)%");

    public static Component format(String input, Player player) {
        //minimessage input, component output passing placeholders through placeholderAPI

        while(true){
            String matched = regexPlaceholderAPIPatern.matcher(input).group(1);
            if(matched != null){
                input = input.replace( matched, PlaceholderAPI.setPlaceholders(player, matched));
            }else{
                break;
            }
        }
        return miniMessage().deserialize(input);
    }
    public static Component format(String input) {
        return format(input, null);

    }

    public static List<Component> format(List<String> inputList, Player player) {
        List<Component> formattedList = new ArrayList<>();

        for(String str : inputList){
            formattedList.add(format(str, player));
        }
        return formattedList;
    }

    public static List<Component> format(List<String> inputList) {
        return format(inputList, null);
    }


    // Used for basic quick chat color format vvv


    public static List<String> formatLore(List<String> inputList) {
        List<String> formattedList = new ArrayList<>();

        for (String str : inputList) {
            String formattedString = Chat.AltFormat(str);
            formattedList.add(formattedString);
        }

        return formattedList;
    }

    public static String AltFormat(final String textToTranslate) {
        final char altColorChar = '&';
        final StringBuilder b = new StringBuilder();
        final char[] mess = textToTranslate.toCharArray();
        boolean color = false, hashtag = false, doubleTag = false;
        char tmp; // Used in loops

        for (int i = 0; i < mess.length; ) { // i increment is handled case by case for speed

            final char c = mess[i];

            if (doubleTag) { // DoubleTag module
                doubleTag = false;

                final int max = i + 3;

                if (max <= mess.length) {
                    // There might be a hex color here
                    boolean match = true;

                    for (int n = i; n < max; n++) {
                        tmp = mess[n];
                        // The order of the checks below is meant to improve performances (i.e. capital letters check is at the end)
                        if (!((tmp >= '0' && tmp <= '9') || (tmp >= 'a' && tmp <= 'f') || (tmp >= 'A' && tmp <= 'F'))) {
                            // It wasn't a hex color, appending found chars to the StringBuilder and continue the for loop
                            match = false;
                            break;
                        }
                    }

                    if (match) {
                        b.append(ChatColor.COLOR_CHAR);
                        b.append('x');

                        // Copy colors with a color code in between
                        for (; i < max; i++) {
                            tmp = mess[i];
                            b.append(ChatColor.COLOR_CHAR);
                            b.append(tmp);
                            // Double the color code
                            b.append(ChatColor.COLOR_CHAR);
                            b.append(tmp);
                        }

                        // i increment has been already done
                        continue;
                    }
                }

                b.append(altColorChar);
                b.append("##");
                // Malformed hex, let's carry on checking mess[i]
            }

            if (hashtag) { // Hashtagmodule
                hashtag = false;

                // Check for double hashtag (&##123 => &#112233)
                if (c == '#') {
                    doubleTag = true;
                    i++;
                    continue;
                }

                final int max = i + 6;

                if (max <= mess.length) {
                    // There might be a hex color here
                    boolean match = true;

                    for (int n = i; n < max; n++) {
                        tmp = mess[n];
                        // The order of the checks below is meant to improve performances (i.e. capital letters check is at the end)
                        if (!((tmp >= '0' && tmp <= '9') || (tmp >= 'a' && tmp <= 'f') || (tmp >= 'A' && tmp <= 'F'))) {
                            // It wasn't a hex color, appending found chars to the StringBuilder and continue the for loop
                            match = false;
                            break;
                        }
                    }

                    if (match) {
                        b.append(ChatColor.COLOR_CHAR);
                        b.append('x');

                        // Copy colors with a color code in between
                        for (; i < max; i++) {
                            b.append(ChatColor.COLOR_CHAR);
                            b.append(mess[i]);
                        }
                        // i increment has been already done
                        continue;
                    }
                }

                b.append(altColorChar);
                b.append('#');
                // Malformed hex, let's carry on checking mess[i]
            }


            if (color) { // Color module
                color = false;

                if (c == '#') {
                    hashtag = true;
                    i++;
                    continue;
                }

                // The order of the checks below is meant to improve performances (i.e. capital letters check is at the end)
                if ((c >= '0' && c <= '9') || (c >= 'a' && c <= 'f') || c == 'r' || (c >= 'k' && c <= 'o') || (c >= 'A' && c <= 'F') || c == 'R' || (c >= 'K' && c <= 'O')) {
                    b.append(ChatColor.COLOR_CHAR);
                    b.append(c);
                    i++;
                    continue;
                }

                b.append(altColorChar);
                // Not a valid color, let's carry on checking mess[i]
            }

            // Base case
            if (c == altColorChar) { // c == '&'
                color = true;
                i++;
                continue;
            }

            // None matched, append current character
            b.append(c);
            i++;

        }

        // Append '&' if '&' was the last character of the string
        if (color)
            b.append(altColorChar);
        else // color and hashtag cannot be true at the same time
            // Append "&#" if "&#" were the last characters of the string
            if (hashtag) {
                b.append(altColorChar);
                b.append('#');
            } else // color, hashtag, and doubleTag cannot be true at the same time
                // Append "&##" if "&##" were the last characters of the string
                if (doubleTag) {
                    b.append(altColorChar);
                    b.append("##");
                }

        return b.toString();
    }
}

