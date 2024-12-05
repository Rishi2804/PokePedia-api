package com.rishi.PokePedia.model.enums;

public enum PokedexRegion {
    NATIONAL("national", "National", null),
    KANTO("kanto", "Kanto", 1),
    ORIGINAL_JOHTO("original-johto", "Johto", 2),
    HOENN("hoenn", "Hoenn", 3),
    ORIGINAL_SINNOH("original-sinnoh", "Sinnoh", 4),
    EXTENDED_SINNOH("extended-sinnoh","Sinnoh", 4),
    UPDATED_JOHTO("updated-johto","Johto", 4),
    ORIGINAL_UNOVA("original-unova","Unova", 5),
    UPDATED_UNOVA("updated-unova","Unova", 5),
    KALOS_CENTRAL("kalos-central","Kalos Central", 6),
    KALOS_COASTAL("kalos-coastal","Kalos Coastal", 6),
    KALOS_MOUNTAIN("kalos-mountain","Kalos Mountain", 6),
    UPDATED_HOENN("updated-hoenn","Hoenn", 6),
    ORIGINAL_ALOLA("original-alola","Alola", 7),
    ORIGINAL_MELEMELE("original-melemele","Melemele", 7),
    ORIGINAL_AKALA("original-akala","Akala", 7),
    ORIGINAL_ULAULA("original-ulaula","Ulaula", 7),
    ORIGINAL_PONI("original-poni","Poni", 7),
    UPDATED_ALOLA("updated-alola","Alola", 7),
    UPDATED_MELEMELE("updated-melemele","Melemele", 7),
    UPDATED_AKALA("updated-akala","Akala", 7),
    UPDATED_ULAULA("updated-ulaula", "Ulaula", 7),
    UPDATED_PONI("updated-poni","Poni", 7),
    LETSGO_KANTO("letsgo-kanto", "Kanto",7),
    GALAR("galar", "Galar", 8),
    ISLE_OF_ARMOR("isle-of-armor", "Isle of Armor", 8),
    CROWN_TUNDRAS("crown-tundra", "Crown Tundra", 8),
    HISUI("hisui", "Hisui",8),
    PALDEA("paldea", "Paldea",9),
    KITAKAMI("kitakami", "Kitakami", 9),
    BLUEBERRY("blueberry", "Blueberry", 9);

    private final String string;
    private final String name;
    private final Integer gen;

    PokedexRegion(String string, String name, Integer gen) {
        this.string = string;
        this.name = name;
        this.gen = gen;
    }

    public String getString() { return string; }
    public String getName() {
        return name;
    }

    public Integer getGen() {
        return gen;
    }

    public static PokedexRegion fromName(String name) {
        for (PokedexRegion region : PokedexRegion.values()) {
            if (region.getString().equalsIgnoreCase(name)) {
                return region;
            }
        }
        throw new IllegalArgumentException("No PokedexRegion with name " + name + " found");
    }
}

