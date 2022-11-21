/*
    Adds Dirt as a valid boost for the Decocative Diffuser, with an amplifier modifier of 2, a duration modifier of 3 with 4 cycles.
*/

// <recipetype:thermal:potion_diffuser_boost>.addBoost(name as string, inputItem as IIngredient, amplifier as int, durationMod as float, cycles as int);

<recipetype:thermal:potion_diffuser_boost>.addBoost("custom_potion_diffuser_boost", <item:minecraft:dirt>, 2, 3.0, 4);

/*
    Removes Glowstone Dust as a valid boost in the Decocative Diffuser.
*/

// <recipetype:thermal:potion_diffuser_boost>.removeBoost(input as IItemStack);

<recipetype:thermal:potion_diffuser_boost>.removeBoost(<item:minecraft:bone_meal>);
