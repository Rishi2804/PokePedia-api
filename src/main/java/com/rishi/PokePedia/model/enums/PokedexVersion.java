package com.rishi.PokePedia.model.enums;

public enum PokedexVersion {
    NATIONAL("national", PokedexRegion.NATIONAL),
    RED_BLUE_YELLOW("red-blue-yellow", PokedexRegion.KANTO),
    GOLD_SILVER_CRYSTAL("gold-silver-crystal", PokedexRegion.ORIGINAL_JOHTO),
    RUBY_SAPPHIRE_EMERALD("ruby-sapphire-emerald", PokedexRegion.HOENN),
    FIRERED_LEAFGREEN("firered-leafgreen", PokedexRegion.KANTO),
    DIAMOND_PEARL_PLATINUM("diamond-pearl-platinum", PokedexRegion.EXTENDED_SINNOH),
    HEARTGOLD_SOULSILVER("heartgold-soulsilver", PokedexRegion.UPDATED_JOHTO),
    BLACK_WHITE("black-white", PokedexRegion.ORIGINAL_UNOVA),
    BLACK_2_WHITE_2("black-2-white-2", PokedexRegion.UPDATED_UNOVA),
    X_Y("x-y", PokedexRegion.KALOS_CENTRAL, PokedexRegion.KALOS_COASTAL, PokedexRegion.KALOS_MOUNTAIN),
    OMEGA_RUBY_ALPHA_SAPPHIRE("omega-ruby-alpha-sapphire", PokedexRegion.UPDATED_HOENN),
    SUN_MOON("sun-moon", PokedexRegion.ORIGINAL_ALOLA, PokedexRegion.ORIGINAL_MELEMELE, PokedexRegion.ORIGINAL_AKALA, PokedexRegion.ORIGINAL_ULAULA, PokedexRegion.ORIGINAL_PONI),
    ULTRA_SUN_ULTRA_MOON("ultra-sun-ultra-moon", PokedexRegion.UPDATED_ALOLA, PokedexRegion.UPDATED_MELEMELE, PokedexRegion.UPDATED_AKALA, PokedexRegion.UPDATED_ULAULA, PokedexRegion.UPDATED_PONI),
    LETS_GO_PIKACHU_LETS_GO_EEVEE("lets-go-pikachu-eevee", PokedexRegion.LETSGO_KANTO),
    SWORD_SHIELD("sword-shield", PokedexRegion.GALAR, PokedexRegion.ISLE_OF_ARMOR, PokedexRegion.CROWN_TUNDRAS),
    BRILLIANT_DIAMOND_AND_SHINING_PEARL("brilliant-diamond-shining-pearl", PokedexRegion.ORIGINAL_SINNOH),
    LEGENDS_ARCEUS("legends-arceus", PokedexRegion.HISUI),
    SCARLET_VIOLET("scarlet-violet", PokedexRegion.PALDEA, PokedexRegion.KITAKAMI, PokedexRegion.BLUEBERRY);

    private final String versionString;  // String representation for the version
    private final PokedexRegion[] regions;

    // Constructor to initialize version string and associated regions
    PokedexVersion(String versionString, PokedexRegion... regions) {
        this.versionString = versionString;
        this.regions = regions;
    }

    // Getter for regions
    public PokedexRegion[] getRegions() {
        return regions;
    }

    public String getString() {
        return versionString;
    }

    public static PokedexVersion fromString(String name) {
        for (PokedexVersion version : PokedexVersion.values()) {
            if (version.getString().equalsIgnoreCase(name)) return version;
        }
        throw new IllegalArgumentException("No PokedexVersion with name " + name + " found");
    }

}