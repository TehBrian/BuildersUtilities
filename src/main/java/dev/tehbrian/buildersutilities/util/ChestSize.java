package dev.tehbrian.buildersutilities.util;

public final class ChestSize {

	private static final int SLOTS_PER_ROW = 9;

	public static final int SINGLE = SLOTS_PER_ROW * 3 /* rows */;
	public static final int DOUBLE = SLOTS_PER_ROW * 6 /* rows */;

	private ChestSize() {
	}

}
