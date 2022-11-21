/*
    Adds two recipes to the Centrifugal Separator.
    The first recipe will, when given a Diamond, use 500 RF to output an Apple 50% of the time, and 25% of the time, output 4 Arrows. The recipe does not output any fluid.
    The second recipe will, when given a Cake, use 500 RF to output 500 Water.
*/

// <recipetype:thermal:centrifuge>.addRecipe(name as string, outputs as Percentaged<IItemStack>[], outputFluid as IFluidStack, ingredient as IIngredient, energy as int);

<recipetype:thermal:centrifuge>.addRecipe("fluidless_custom_bottler_recipe", [<item:minecraft:apple> % 50, (<item:minecraft:arrow> * 4) % 25], <fluid:minecraft:empty>, <item:minecraft:diamond>, 500);
<recipetype:thermal:centrifuge>.addRecipe("fluid_custom_bottler_recipe", [], <fluid:minecraft:water> * 500, <item:minecraft:cake>, 500);

/*
    Removes two recipes from the Centrifugal Seperator.
    The first recipe that is removed is the recipe for Red Dye.
    The second recipe that is removed is the recipe that takes a Syrup Bottle, and gives an empty Glass Bottle and the Syrup fluid.
*/

// <recipetype:thermal:centrifuge>.removeRecipe(output as IItemStack);
// <recipetype:thermal:centrifuge>.removeRecipe(output as IItemStack[], fluidOutputs as IFluidStack[]);

<recipetype:thermal:centrifuge>.removeRecipe(<item:minecraft:red_dye>);
<recipetype:thermal:centrifuge>.removeRecipe([<item:minecraft:glass_bottle>], [<fluid:thermal:syrup>]);
