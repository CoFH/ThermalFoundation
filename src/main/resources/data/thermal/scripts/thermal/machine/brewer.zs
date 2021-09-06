/*
    Adds two recipes to the Alchemical Imbuer.
    The first recipe, "custom_brewer_recipe", will, at a cost of 500 RF, output 250 mB of Lava by Brewing 250 mB of Water, using a Cookie as a catalyst.
    The second recipe, "mock_brewer_recipe", will, at a cost of 500 RF, output 250 mB of Water by Brewing 250 mB of any Fluid in the <tag:fluids:forge:latex> tag, using a Sponge as a catalyst.

    "mock_brewer_recipe" is only added so that it can be removed later, as Thermal does not ship with any default recipes that can be removed.
*/

// <recipetype:thermal:brewer>.addRecipe(name as string, output as IFluidStack, ingredient as IIngredient, fluidInput as IFluidStack, energy as int);

<recipetype:thermal:brewer>.addRecipe("custom_brewer_recipe", <fluid:minecraft:lava> * 250, <item:minecraft:cookie>, <fluid:minecraft:water> * 250, 500);
<recipetype:thermal:brewer>.addRecipe("mock_brewer_recipe", <fluid:minecraft:water> * 250, <item:minecraft:sponge>, <tag:fluids:forge:latex> * 250, 500);

/*
    Removes all Alchemical Imbuer recipes that output Water.

    This will remove the "mock_brewer_recipe" recipe that was added above.
*/

// <recipetype:thermal:brewer>.removeRecipe(output as IFluidStack);

<recipetype:thermal:brewer>.removeRecipe(<fluid:minecraft:water>);
