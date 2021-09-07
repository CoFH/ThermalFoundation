/*
    Adds three recipes to the Pyrolyzer.
    The first recipe, "fluidless_custom_pyrolyzer_recipe", will, at a cost of 500 RF, output a Diamond when Dirt is Pyrolized.
    The second recipe, "fluid_custom_pyrolyzer_recipe", will, at a cost of 500 RF, output an Arrow and 100 mB of Latex when White Wool is Pyrolyzed.
    The third recipe, "mock_pyrolyzer_recipe", will, at a cost of 500 RF, output Glass when an Apple is Pyrolized.

    "mock_pyrolyzer_recipe" is only added so that it can be removed later, as Thermal does not ship with any default recipes that can be removed with just an IItemStack.
*/

// <recipetype:thermal:pyrolyzer>.addRecipe(name as string, outputs as MCWeightedItemStack[], outputFluid as IFluidStack, ingredient as IIngredient, energy as int);

<recipetype:thermal:pyrolyzer>.addRecipe("fluidless_custom_pyrolyzer_recipe", [<item:minecraft:diamond>], <fluid:minecraft:empty>, <item:minecraft:dirt>, 500);
<recipetype:thermal:pyrolyzer>.addRecipe("fluid_custom_pyrolyzer_recipe", [<item:minecraft:arrow>], <fluid:thermal:latex> * 100, <item:minecraft:white_wool>, 500);
<recipetype:thermal:pyrolyzer>.addRecipe("mock_pyrolyzer_recipe", [<item:minecraft:glass>], <fluid:minecraft:empty>, <item:minecraft:apple>, 500);

/*
    Removes two recipes from the Pyrolyzer.
    The first recipe that is removed is the recipe that outputs Glass.
    This will remove the "mock_pyrolyzer_recipe" recipe that was added above.

    The second recipe that is removed is the recipe that outputs Coal Coke, Tar and Creosote.
*/

// <recipetype:thermal:pyrolyzer>.removeRecipe(output as IItemStack);
// <recipetype:thermal:pyrolyzer>.removeRecipe(output as IItemStack[], fluidOutputs as IFluidStack[]);

<recipetype:thermal:pyrolyzer>.removeRecipe(<item:minecraft:glass>);
<recipetype:thermal:pyrolyzer>.removeRecipe([<item:thermal:coal_coke>, <item:thermal:tar>], [<fluid:thermal:creosote>]);
