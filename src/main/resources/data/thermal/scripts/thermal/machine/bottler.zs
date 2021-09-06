/*
    Adds a recipe to the Fluid Encapsulator that will output an Apple, when an Arrow is Filled with 250 mB of Water.
*/

// <recipetype:thermal:bottler>.addRecipe(name as string, output as IItemStack, ingredient as IIngredient, fluidInput as CTFluidIngredient, energy as int);

<recipetype:thermal:bottler>.addRecipe("custom_bottler_recipe", <item:minecraft:apple>, <item:minecraft:arrow>, <fluid:minecraft:water> * 250, 500);

/*
    Removes all Fluid Encapsulator recipes that Black Concrete.
*/

// <recipetype:thermal:brewer>.removeRecipe(output as IItemStack);

<recipetype:thermal:bottler>.removeRecipe(<item:minecraft:black_concrete>);
