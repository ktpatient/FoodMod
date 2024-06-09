package com.kitp13.food.items.tools.modifiers;

import java.util.HashMap;

public class ModifiersRegistry {
    public static HashMap<String, Modifiers> MODIFIERS_MAP= new HashMap<>();
    static {
        MODIFIERS_MAP.put(BrittleModifier.NAME, new BrittleModifier());
        MODIFIERS_MAP.put(VampiricModifier.NAME, new VampiricModifier());
        MODIFIERS_MAP.put(TestModifier.NAME,new TestModifier());
        MODIFIERS_MAP.put(MiningExpModifier.NAME,new MiningExpModifier());
    }
}
