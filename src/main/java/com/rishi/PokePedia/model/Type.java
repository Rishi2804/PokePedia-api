package com.rishi.PokePedia.model;

public enum Type {
    FIRE,
    WATER,
    GRASS,
    ELECTRIC,
    ICE,
    FIGHTING,
    POISON,
    GROUND,
    FLYING,
    PSYCHIC,
    BUG,
    ROCK,
    GHOST,
    DRAGON,
    DARK,
    STEEL,
    FAIRY,
    NORMAL;

    private final String lowercaseName;

    Type() {
        this.lowercaseName = this.name().toLowerCase();
    }

    public String getName() {
        return lowercaseName;
    }

    public static Type fromString(String typeName) {
        if (typeName == null) {
            throw new IllegalArgumentException("Type name cannot be null");
        }
        try {
            // Convert input to uppercase to match enum values
            return Type.valueOf(typeName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid Type name: " + typeName);
        }
    }
}
