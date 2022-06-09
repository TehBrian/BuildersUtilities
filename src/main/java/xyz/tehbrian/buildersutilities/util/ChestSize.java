package xyz.tehbrian.buildersutilities.util;

public class ChestSize {

    private ChestSize() {
    }

    private static final int SLOTS_PER_ROW = 9;

    public static final int SINGLE = SLOTS_PER_ROW * 3 /* rows */;
    public static final int DOUBLE = SLOTS_PER_ROW * 6 /* rows */;

}
