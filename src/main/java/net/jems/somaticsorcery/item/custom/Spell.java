package net.jems.somaticsorcery.item.custom;


public class Spell {
    private final String name;
    private final String symbol;

    private final int level;

    public Spell(String name, String symbol, int level) {
        this.name = name;
        this.symbol = symbol;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getLevel() {
        return level;
    }

}
