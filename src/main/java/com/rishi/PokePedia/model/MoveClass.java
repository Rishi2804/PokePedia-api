package com.rishi.PokePedia.model;

public enum MoveClass {
    PHYSICAL,
    SPECIAL,
    STATUS;

    public static MoveClass fromString(String typeName) {
        if (typeName == null) {
            throw new IllegalArgumentException("Type name cannot be null");
        }
        try {
            return MoveClass.valueOf(typeName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid Type name: " + typeName);
        }
    }
}
