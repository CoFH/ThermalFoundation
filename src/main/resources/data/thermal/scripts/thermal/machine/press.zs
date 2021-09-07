/*
    Adds three recipes to the Multiservo Press.
    The first recipe will use 500 RF and will Press Dirt into a Diamond.
    The second recipe will use 500 RF and will Press Glass into 250 mB of Water.
    The third recipe will use 500 RF and will Press an Arrow into an Apple and 250 mB of Lava.
*/

// <recipetype:thermal:press>.addRecipe(name as string, outputs as MCWeightedItemStack[], outputFluid as IFluidStack, ingredients as IIngredient[], int energy);

<recipetype:thermal:press>.addRecipe("fluidless_custom_press_recipe", [<item:minecraft:diamond>], <fluid:minecraft:empty>, [<item:minecraft:dirt>], 500);
<recipetype:thermal:press>.addRecipe("itemless_custom_press_recipe", [], <fluid:minecraft:water> * 250, [<item:minecraft:glass>], 500);
<recipetype:thermal:press>.addRecipe("custom_press_recipe", [<item:minecraft:apple>], <fluid:minecraft:lava> * 250, [<item:minecraft:arrow>], 500);

/*
    Removes two recipes from the Multiservo Press.
    The first recipe that is removed is the recipe for Red Sandstone.
    The second script removes all recipes that output Latex.
*/

// <recipetype:thermal:press>.removeRecipe(output as IItemStack);
// <recipetype:thermal:press>.removeRecipe(output as IItemStack[], fluidOutputs as IFluidStack[]);

<recipetype:thermal:press>.removeRecipe(<item:minecraft:red_sandstone>);
<recipetype:thermal:press>.removeRecipe([], [<fluid:thermal:latex>]);
