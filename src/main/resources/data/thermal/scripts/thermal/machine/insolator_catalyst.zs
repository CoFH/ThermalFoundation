/*
    Adds a diamond as a valid Phytogenic Insulator Catalyst, providing a primary modifier of 5.0, a secondary modifier of 3.25, an energy modifier of 0.9, a minimum chance of 2 and a use chance of 0.8.
*/

// <recipetype:thermal:insolator_catalyst>.addCatalyst(name as string, ingredient as IIngredient, primaryMod as float, secondaryMod as float, energyMod as float, minChance as float, useChance as float);

<recipetype:thermal:insolator_catalyst>.addCatalyst("custom_insolator_catalyst", <item:minecraft:diamond>, 5.0, 3.25, 0.9, 2, 0.8);

/*
    Removes the ability for Bone Meal to be used as a Catalyst in the Phytogenic Insulator.
*/

// <recipetype:thermal:insolator_catalyst>.removeCatalyst(input as IItemStack);

<recipetype:thermal:insolator_catalyst>.removeCatalyst(<item:minecraft:bone_meal>);
