/*
    Adds a recipe to the Pulverizer that will use 500 RF to pulverize Dirt into a Diamond and an Iron Ingot and produce 3 experience.
*/

// <recipetype:thermal:pulverizer>.addRecipe(name as string, outputs as Percentaged<IItemStack>[], ingredient as IIngredient, experience as float, energy as int);

<recipetype:thermal:pulverizer>.addRecipe("custom_furnace_recipe", [<item:minecraft:diamond>, <item:minecraft:iron_ingot>], <item:minecraft:dirt>, 3, 500);

/*
    Removes the recipe for Invar Dust from the Pulverizer.
*/

// <recipetype:thermal:pulverizer>.removeRecipe(output as IItemStack);

<recipetype:thermal:pulverizer>.removeRecipe(<item:thermal:invar_dust>);
