package com.kitp13.food.items.tools.modifiers;

import java.util.HashMap;

public class ModifiersRegistry {
    public static HashMap<String, Modifiers> MODIFIERS_MAP= new HashMap<>();
    public static void RegisterModifier(String accessorName, Modifiers modifier){
        MODIFIERS_MAP.put(accessorName,modifier);
    }
}
