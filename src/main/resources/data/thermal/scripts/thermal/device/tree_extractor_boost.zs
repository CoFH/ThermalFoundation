/*
    Adds Dirt as a valid boost for the Tree Extractor, with an output modifier of 80 with 80 cycles.
*/

// <recipetype:thermal:tree_extractor_boost>.addBoost(name as string, inputItem as IIngredient, outputMod as float, cycles as int);

<recipetype:thermal:tree_extractor_boost>.addBoost("custom_tree_extractor_boost", <item:minecraft:dirt>, 80, 80);

/*
    Removes Bone Meal as a valid boost in the Tree Extractor.
*/

// <recipetype:thermal:tree_extractor_boost>.removeBoost(input as IItemStack);

<recipetype:thermal:tree_extractor_boost>.removeBoost(<item:minecraft:bone_meal>);
