package com.rishi.PokePedia.model.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public enum VersionGroup {
    NATIONAL("national", 10, PokedexRegion.NATIONAL),
    RED_BLUE("red-blue", 1, PokedexRegion.KANTO),
    YELLOW("yellow", 1, PokedexRegion.KANTO),
    GOLD_SILVER("gold-silver", 2, PokedexRegion.ORIGINAL_JOHTO),
    CRYSTAL("crystal", 2, PokedexRegion.ORIGINAL_JOHTO),
    RUBY_SAPPHIRE("ruby-sapphire", 3, PokedexRegion.HOENN),
    EMERALD("emerald", 3, PokedexRegion.HOENN),
    FIRERED_LEAFGREEN("firered-leafgreen", 3, PokedexRegion.KANTO),
    DIAMOND_PEARL("diamond-pearl", 4, PokedexRegion.ORIGINAL_SINNOH),
    PLATINUM("platinum", 4, PokedexRegion.EXTENDED_SINNOH),
    HEARTGOLD_SOULSILVER("heartgold-soulsilver", 4, PokedexRegion.UPDATED_JOHTO),
    BLACK_WHITE("black-white", 5, PokedexRegion.ORIGINAL_UNOVA),
    BLACK_2_WHITE_2("black-2-white-2", 5, PokedexRegion.UPDATED_UNOVA),
    X_Y("x-y", 6, PokedexRegion.KALOS_CENTRAL, PokedexRegion.KALOS_COASTAL, PokedexRegion.KALOS_MOUNTAIN),
    OMEGA_RUBY_ALPHA_SAPPHIRE("omega-ruby-alpha-sapphire", 6, PokedexRegion.UPDATED_HOENN),
    SUN_MOON("sun-moon", 7, PokedexRegion.ORIGINAL_ALOLA, PokedexRegion.ORIGINAL_MELEMELE, PokedexRegion.ORIGINAL_AKALA, PokedexRegion.ORIGINAL_ULAULA, PokedexRegion.ORIGINAL_PONI),
    ULTRA_SUN_ULTRA_MOON("ultra-sun-ultra-moon", 7, PokedexRegion.UPDATED_ALOLA, PokedexRegion.UPDATED_MELEMELE, PokedexRegion.UPDATED_AKALA, PokedexRegion.UPDATED_ULAULA, PokedexRegion.UPDATED_PONI),
    LETS_GO_PIKACHU_LETS_GO_EEVEE("lets-go-pikachu-lets-go-eevee", 7, PokedexRegion.LETSGO_KANTO),
    SWORD_SHIELD("sword-shield", 8, PokedexRegion.GALAR, PokedexRegion.ISLE_OF_ARMOR, PokedexRegion.CROWN_TUNDRAS),
    BRILLIANT_DIAMOND_AND_SHINING_PEARL("brilliant-diamond-and-shining-pearl", 8, PokedexRegion.ORIGINAL_SINNOH),
    LEGENDS_ARCEUS("legends-arceus", 8, PokedexRegion.HISUI),
    SCARLET_VIOLET("scarlet-violet", 9, PokedexRegion.PALDEA, PokedexRegion.KITAKAMI, PokedexRegion.BLUEBERRY);

    private final String versionName;
    private final Integer gen;
    private final PokedexRegion[] regions;

    VersionGroup(String versionName, Integer gen, PokedexRegion... regions) {
        this.versionName = versionName;
        this.gen = gen;
        this.regions = regions;
    }

    public String getVersionName() {
        return versionName;
    }

    public Integer getGen() { return gen; }

    public PokedexRegion[] getRegions() { return regions; }

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
