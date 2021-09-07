/*
    Adds two recipes to the Blast Chiller.
    The first recipe will use 500 RF to Chill 200 mB of Syrup into a Cake.
    The second recipe will use 500 RF to Chill 1000mB of any Fluid in the <tag:fluids:forge:latex> tag and a Stick to make a Diamond.
*/

// <recipetype:thermal:chiller>.addRecipe(name as string, output as IItemStack, ingredient as IIngredient, inputFluid as CTFluidIngredient, energy as int);

<recipetype:thermal:chiller>.addRecipe("itemless_custom_chiller_recipe", <item:minecraft:cake>, <item:minecraft:air>, <fluid:thermal:syrup> * 200, 500);
<recipetype:thermal:chiller>.addRecipe("item_custom_chiller_recipe", <item:minecraft:diamond>, <item:minecraft:stick>, <tag:fluids:forge:latex> * 100 , 500);

/*
    Removes all recipes from the Blast Chiller that outpu a Honey Block.
*/

// <recipetype:thermal:chiller>.removeRecipe(output as IItemStack);

<recipetype:thermal:chiller>.removeRecipe(<item:minecraft:honey_block>);
