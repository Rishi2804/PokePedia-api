package com.rishi.PokePedia.model;

public enum MoveClass {
    PHYSICAL,
    SPECIAL,
    STATUS;

    public static Type fromString(String typeName) {
        if (typeName == null) {
            throw new IllegalArgumentException("Type name cannot be null");
        }
        try {
            return Type.valueOf(typeName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid Type name: " + typeName);
        }
    }
}
