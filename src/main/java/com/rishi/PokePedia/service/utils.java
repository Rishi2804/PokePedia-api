package com.rishi.PokePedia.service;

import java.util.Arrays;

public class utils {
    public static String formatName(String name, boolean pokemon) {
        // Split the name by dash
        String[] words = name.split("-");

        if (pokemon) {
            if (name.equals("jangmo-o") || name.equals("hakamo-o") || name.equals("kommo-o")) {
                return capitalizeFirstLetter(name);
            }
            if (name.equals("ho-oh")) {
                return "Ho-Oh";
            }

            // Sort the words based on special rules
            Arrays.sort(words, (a, b) -> {
                if (isSpecial(a)) {
                    return -1; // 'mega' comes before other strings
                } else if (isSpecial(b)) {
                    return 1; // Other strings come after 'mega'
                } else {
                    return 0; // No preference for other strings
                }
            });

            // Handle special cases for the first word
            switch (words[0]) {
                case "alola":
                    words[0] = "alolan";
                    break;
                case "galar":
                    words[0] = "galarian";
                    break;
                case "hisui":
                    words[0] = "hisuian";
                    break;
                case "paldea":
                    words[0] = "paldean";
                    break;
                case "50":
                    words[0] = "50%";
                    break;
                case "10":
                    words[0] = "10%";
                    break;
                default:
                    break;
            }
        }

        // Capitalize the first letter of each word
        for (int i = 0; i < words.length; i++) {
            words[i] = capitalizeFirstLetter(words[i]);
        }

        // Join the words with space
        return String.join(" ", words);
    }

    private static boolean isSpecial(String word) {
        return word.equals("mega") || word.equals("gmax") || word.equals("primal") || word.equals("ash") ||
                word.equals("50") || word.equals("10") || word.equals("complete") ||
                word.equals("alola") || word.equals("galar") || word.equals("hisui") || word.equals("paldea");
    }

    private static String capitalizeFirstLetter(String word) {
        if (word.isEmpty()) return word;
        return Character.toUpperCase(word.charAt(0)) + word.substring(1);
    }
}
