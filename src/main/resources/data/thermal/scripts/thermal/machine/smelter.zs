/*
    Adds two recipes the the Induction Smelter.
    The first recipe will use 500 RF, Glass and an Arrow to output a Diamond, and 50% of the time an Apple.
    The second recipe will use 500 RF, Dirt and a Diamond to output Bone Meal, and Rich Slag.
*/

// <recipetype:thermal:smelter>.addRecipe(name as string, outputs as Percentaged<IItemStack>[], ingredients as IIngredient[], experience as float, energy as int);

<recipetype:thermal:smelter>.addRecipe("custom_smelter_recipe", [<item:minecraft:diamond>, <item:minecraft:apple> % 50], [<item:minecraft:glass>, <item:minecraft:arrow>], 3, 500);
<recipetype:thermal:smelter>.addRecipe("slag_custom_smelter_recipe", [<item:minecraft:bone_meal>, <item:thermal:rich_slag>], [<item:minecraft:dirt>, <item:minecraft:diamond>], 2, 500);

/*
    Removes the Smelter recipe that outputs Emeralds and Rich Slag.
*/

// <recipetype:thermal:smelter>.removeRecipe(input as IItemStack[]);

<recipetype:thermal:smelter>.removeRecipe([<item:minecraft:emerald>, <item:thermal:rich_slag>]);
