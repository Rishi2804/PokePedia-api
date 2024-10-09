package com.rishi.PokePedia.model.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public enum VersionGroup {
    RED_BLUE("red-blue"),
    YELLOW("yellow"),
    GOLD_SILVER("gold-silver"),
    CRYSTAL("crystal"),
    RUBY_SAPPHIRE("ruby-sapphire"),
    EMERALD("emerald"),
    FIRERED_LEAFGREEN("firered-leafgreen"),
    DIAMOND_PEARL("diamond-pearl"),
    PLATINUM("platinum"),
    HEARTGOLD_SOULSILVER("heartgold-soulsilver"),
    BLACK_WHITE("black-white"),
    BLACK_2_WHITE_2("black-2-white-2"),
    X_Y("x-y"),
    OMEGA_RUBY_ALPHA_SAPPHIRE("omega-ruby-alpha-sapphire"),
    SUN_MOON("sun-moon"),
    ULTRA_SUN_ULTRA_MOON("ultra-sun-ultra-moon"),
    LETS_GO_PIKACHU_LETS_GO_EEVEE("lets-go-pikachu-lets-go-eevee"),
    SWORD_SHIELD("sword-shield"),
    BRILLIANT_DIAMOND_AND_SHINING_PEARL("brilliant-diamond-and-shining-pearl"),
    LEGENDS_ARCEUS("legends-arceus"),
    SCARLET_VIOLET("scarlet-violet");

    private final String versionName;

    VersionGroup(String versionName) {
        this.versionName = versionName;
    }

    public String getVersionName() {
        return versionName;
    }

    public static VersionGroup fromName(String name) {
        for (VersionGroup vgroup : VersionGroup.values()) {
            if (vgroup.getVersionName().equalsIgnoreCase(name)) {
                return vgroup;
            }
        }
        throw new IllegalArgumentException("No VersionGroup with name " + name + " found");
    }

    public static final Comparator<VersionGroup> ORDER = Comparator.comparingInt(Enum::ordinal);

    public static void sort(VersionGroup[] array) {
        Arrays.sort(array, ORDER);
    }
    public static void sort(List<VersionGroup> list) {
        Collections.sort(list, ORDER);
    }
}
