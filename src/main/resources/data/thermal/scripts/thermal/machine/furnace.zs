/*
    Adds a recipe to the Redstone Furnace that will use 500 RF to smelt Dirt into a Diamond and produce 3 experience.
*/

// <recipetype:thermal:furnace>.addRecipe(name as string, output as IItemStack, ingredient as IIngredient, experience as float, energy as int);

<recipetype:thermal:furnace>.addRecipe("custom_furnace_recipe", <item:minecraft:diamond>, <item:minecraft:dirt>, 3, 500);

/*
    Removes the recipe for Rotten Flesh to Leather from the Redstone Furnace.
*/

// <recipetype:thermal:furnace>.removeRecipe(output as IItemStack);

<recipetype:thermal:furnace>.removeRecipe(<item:minecraft:leather>);
