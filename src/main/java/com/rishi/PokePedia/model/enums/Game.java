package com.rishi.PokePedia.model.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public enum Game {
    RED("red"),
    BLUE("blue"),
    YELLOW("yellow"),
    GOLD("gold"),
    SILVER("silver"),
    CRYSTAL("crystal"),
    RUBY("ruby"),
    SAPPHIRE("sapphire"),
    EMERALD("emerald"),
    FIRERED("firered"),
    LEAFGREEN("leafgreen"),
    DIAMOND("diamond"),
    PEARL("pearl"),
    PLATINUM("platinum"),
    HEARTGOLD("heartgold"),
    SOULSILVER("soulsilver"),
    BLACK("black"),
    WHITE("white"),
    BLACK_2("black-2"),
    WHITE_2("white-2"),
    X("x"),
    Y("y"),
    OMEGA_RUBY("omega-ruby"),
    ALPHA_SAPPHIRE("alpha-sapphire"),
    SUN("sun"),
    MOON("moon"),
    ULTRA_SUN("ultra-sun"),
    ULTRA_MOON("ultra-moon"),
    LETS_GO_PIKACHU("lets-go-pikachu"),
    LETS_GO_EEVEE("lets-go-eevee"),
    SWORD("sword"),
    SHIELD("shield"),
    BRILLIANT_DIAMOND("brilliant-diamond"),
    SHINING_PEARL("shining-pearl"),
    LEGENDS_ARCEUS("legends-arceus"),
    SCARLET("scarlet"),
    VIOLET("violet");

    private final String gameName;

    Game(String gameName) {
        this.gameName = gameName;
    }

    public String getGameName() {
        return gameName;
    }

    public static Game fromName(String name) {
        for (Game game : Game.values()) {
            if (game.getGameName().equalsIgnoreCase(name)) {
                return game;
            }
        }
        throw new IllegalArgumentException("No Game with name " + name + " found");
    }

    public static final Comparator<Game> ORDER = Comparator.comparingInt(Enum::ordinal);

    public static void sort(Game[] array) {
        Arrays.sort(array, ORDER);
    }

    public static void sort(List<Game> list) {
        Collections.sort(list, ORDER);
    }
}
