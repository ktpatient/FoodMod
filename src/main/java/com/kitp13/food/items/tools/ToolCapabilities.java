package com.kitp13.food.items.tools;

public enum ToolCapabilities {
    PICKAXE(1), // 0001
    AXE(2),     // 0010
    SHOVEL(4);  // 0100

    private final int bit;

    ToolCapabilities(int bit) {
        this.bit = bit;
    }

    public int getBit() {
        return bit;
    }

    public static int combine(ToolCapabilities... capabilities) {
        int combined = 0;
        for (ToolCapabilities capability : capabilities) {
            combined |= capability.getBit();
        }
        return combined;
    }

    public static boolean hasCapability(int combined, ToolCapabilities capability) {
        return (combined & capability.getBit()) != 0;
    }
}

