/*
    Adds a recipe to the Phytogenic Insulator that will use 250 RF and 500 mB of Water and Glass to output a Diamond, and 50% of the time an Apple. 
*/

// <recipetype:thermal:insolator>.addRecipe(name as name, outputs as MCWeightedItemStack[], ingredient as IIngredient, fluidAmount as int, energy as int);

<recipetype:thermal:insolator>.addRecipe("custom_insolator_recipe", [<item:minecraft:diamond>, <item:minecraft:apple> % 50], <item:minecraft:glass>, 500, 250);

/*
    Removes the ability for Bone Meal to be used as a Catalyst in the Phytogenic Insulator.
*/

// <recipetype:thermal:insolator>.removeRecipe(input as IItemStack);

<recipetype:thermal:insolator>.removeRecipe(<item:minecraft:nether_wart>);
