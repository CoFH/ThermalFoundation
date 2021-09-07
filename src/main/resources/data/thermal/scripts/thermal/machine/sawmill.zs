/*
    Adds a recipe to the Sawmill that will use 500 RF and Glass to output a Diamond, and 50% of the time an Apple. 
*/

// <recipetype:thermal:sawmill>.addRecipe(name as string, outputs as MCWeightedItemStack[], ingredient as IIngredient, energy as int);

<recipetype:thermal:sawmill>.addRecipe("custom_sawmill_recipe", [<item:minecraft:diamond>, <item:minecraft:apple> % 50], <item:minecraft:glass>, 500);

/*
    Removes the Sawmill recipe that outputs Frost Melon Slices and Frost Melon Seeds.
*/

// <recipetype:thermal:sawmill>.removeRecipe(input as IItemStack[]);

<recipetype:thermal:sawmill>.removeRecipe([<item:thermal:frost_melon_slice>, <item:thermal:frost_melon_seeds>]);
