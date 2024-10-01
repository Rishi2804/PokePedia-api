package com.rishi.PokePedia.model;

public enum LearnMethod {
    LEVEL_UP("level-up"),
    MACHINE("machine"),
    EGG("egg"),
    TUTOR("tutor"),
    LIGHT_BALL_EGG("light-ball-egg"),
    FORM_CHANGE("form-change"),
    ZYGARDE_CUBE("zygarde-cube");

    private final String name;

    LearnMethod(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public static LearnMethod fromName(String name) {
        for (LearnMethod method : LearnMethod.values()) {
            if (method.getName().equalsIgnoreCase(name)) {
                return method;
            }
        }
        throw new IllegalArgumentException("No LearnMethod with name " + name + " found");
    }
}
