/*
    Adds Dirt as a valid boost for the Aquatic Entangler, pulling from the <resource:minecraft:chests/simple_dungeon> loot table, with an output modifier of 0.5, and a use chance of 0.8.
*/

// <recipetype:thermal:fisher_boost>.addBoost(name as string, inputItem as IIngredient, lootTable as ResourceLocation, outputMod as float, useChance as float);

<recipetype:thermal:fisher_boost>.addBoost("custom_fisher_boost", <item:minecraft:dirt>, <resource:minecraft:chests/simple_dungeon>, 0.5, 0.8);

/*
    Removes Aqua-Chow as a valid boost in the Aquatic Entangler.
*/

// <recipetype:thermal:fisher_boost>.removeBoost(input as IItemStack);

<recipetype:thermal:fisher_boost>.removeRecipe(<item:thermal:aquachow>);
