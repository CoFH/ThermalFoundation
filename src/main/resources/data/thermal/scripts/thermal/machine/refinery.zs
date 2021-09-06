/*
    Adds three recipes to the Fractioning Still.
    The first recipe, "custom_refinery_recipe", will, at a cost of 500 RF, output a Diamond, 250 mB of Water and 800 mB of Syrup when 1000 mB of Water is Refined.
    The second recipe, "itemless_custom_refinery_recipe", will, at a cost of 500 RF, output 250 mB of LAva and 100 mB of Latex when 500 mB of Syrup is Refined.
    The third recipe, "mock_fluidless_custom_refinery_recipe", will, at a cost of 500 RF, output Dirt, when 250 mB of Lava is Refined.

    "mock_fluidless_custom_refinery_recipe" is only added so that it can be removed later, as Thermal does not ship with any default recipes that can be removed with just an IItemStack.
*/

// <recipetype:thermal:refinery>.addRecipe(name as string, itemOutput as MCWeightedItemStack, fluidsOutput as IFluidStack[], inputFluid as CTFluidIngredient, energy as int);

<recipetype:thermal:refinery>.addRecipe("custom_refinery_recipe", <item:minecraft:diamond>, [<fluid:minecraft:water> * 250, <fluid:thermal:syrup> * 800], <tag:fluids:minecraft:water>, 500);
<recipetype:thermal:refinery>.addRecipe("itemless_custom_refinery_recipe", <item:minecraft:air>, [<fluid:minecraft:lava> * 250, <fluid:thermal:latex> * 100], <fluid:thermal:syrup> * 500, 500);
<recipetype:thermal:refinery>.addRecipe("mock_fluidless_custom_refinery_recipe", <item:minecraft:dirt>, [], <fluid:minecraft:lava> * 250, 500);

/*
    Removes two recipes from the Fractioning Still.
    The first recipe that is removed is the recipe that outputs Dirt.
    This will remove the "mock_fluidless_custom_refinery_recipe" recipe that was added above.

    The second recipe that is removed is the recipe that outputs Rosin and Tree Oil.
*/

// <recipetype:thermal:refinery>.removeRecipe(output as IItemStack);
// <recipetype:thermal:refinery>.removeRecipe(output as IItemStack[], fluidOutputs as IFluidStack[]);

<recipetype:thermal:refinery>.removeRecipe(<item:minecraft:dirt>);
<recipetype:thermal:refinery>.removeRecipe([<item:thermal:rosin>], [<fluid:thermal:tree_oil>]);
 