/*
    Adds a recipe to the Magma Crucible that will use 500 RF and will take a Diamond as an input to produce 2 mB of Syrup.
*/

// <recipetype:thermal:crucible>.addRecipe(name as string, output as IFluidStack, ingredient as IIngredient, energy as int);

<recipetype:thermal:crucible>.addRecipe("custom_crucible_recipe", <fluid:thermal:syrup> * 2, <item:minecraft:diamond>, 500);

/*
    Removes all recipes from the Magma Crucible that output Water.
*/

// <recipetype:thermal:crucible>.removeRecipe(output as IFluidStack);

<recipetype:thermal:crucible>.removeRecipe(<fluid:minecraft:water>);
