package net.jems.somaticsorcery.item.custom;


public class Spell {
    private final String name;
    private final String[] symbols;

    public Spell(String name, String[] symbols) {
        this.name = name;
        this.symbols = symbols;
    }

    public String getName() {
        return name;
    }

    public boolean checkSymbol(String symbol) {
        for (String i : symbols) {
            if (i.equals(symbol)) {
                return true;
            }
        }
        return false;
    }

}
