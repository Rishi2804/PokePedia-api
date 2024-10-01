package com.rishi.PokePedia.model;

public enum PokedexRegion {
    KANTO("kanto", 1),
    ORIGINAL_JOHTO("original-johto", 2),
    HOENN("hoenn", 3),
    ORIGINAL_SINNOH("original-sinnoh", 4),
    EXTENDED_SINNOH("extended-sinnoh", 4),
    UPDATED_JOHTO("updated-johto", 4),
    ORIGINAL_UNOVA("original-unova", 5),
    UPDATED_UNOVA("updated-unova", 5),
    KALOS_CENTRAL("kalos-central", 6),
    KALOS_COASTAL("kalos-coastal", 6),
    KALOS_MOUNTAIN("kalos-mountain", 6),
    UPDATED_HOENN("updated-hoenn", 6),
    ORIGINAL_ALOLA("original-alola", 7),
    ORIGINAL_MELEMELE("original-melemele", 7),
    ORIGINAL_AKALA("original-akala", 7),
    ORIGINAL_ULAULA("original-ulaula", 7),
    ORIGINAL_PONI("original-poni", 7),
    UPDATED_ALOLA("updated-alola", 7),
    UPDATED_MELEMELE("updated-melemele", 7),
    UPDATED_AKALA("updated-akala", 7),
    UPDATED_ULAULA("updated-ulaula", 7),
    UPDATED_PONI("updated-poni", 7),
    LETSGO_KANTO("letsgo-kanto", 7),
    GALAR("galar", 8),
    ISLE_OF_ARMOR("isle-of-armor", 8),
    CROWN_TUNDRAS("crown-tundra", 8),
    HISUI("hisui", 8),
    PALDEA("paldea", 9),
    KITAKAMI("kitakami", 9),
    BLUEBERRY("blueberry", 9);

    private final String name;
    private final Integer gen;

    PokedexRegion(String name, Integer gen) {
        this.name = name;
        this.gen = gen;
    }

    public String getName() {
        return name;
    }

    public Integer getGen() {
        return gen;
    }

    public static PokedexRegion fromName(String name) {
        for (PokedexRegion region : PokedexRegion.values()) {
            if (region.getName().equalsIgnoreCase(name)) {
                return region;
            }
        }
        throw new IllegalArgumentException("No PokedexRegion with name " + name + " found");
    }
}

