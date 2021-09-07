/*
    Adds two mappings to the Hive Exactor.
    The first mapping, "custom_hive_extractor_recipe", will output an Iron Ingot and 10 mB of Lava when a Bee Nest is Extracted.
    The second mapping, "mock_hive_extractor_recipe", will output a Gold Ingot and 10 mB of Water when a Beehive is Extracted.

    "mock_hive_extractor_recipe" is only added so that it can be removed later, as Thermal does not ship with any default mappings that can be removed.
*/

// <recipetype:thermal:hive_extractor>.addMapping(name as string, hive as MCBlock, item as IItemStack, fluid as IFluidStack);

<recipetype:thermal:hive_extractor>.addMapping("custom_hive_extractor_recipe", <block:minecraft:bee_nest>, <item:minecraft:iron_ingot>, <fluid:minecraft:lava> * 10);
<recipetype:thermal:hive_extractor>.addMapping("mock_hive_extractor_recipe", <block:minecraft:beehive>, <item:minecraft:gold_ingot>, <fluid:minecraft:water> * 10);

/*
    Removes the Beehive mapping from the Hive Extractor.
*/

// <recipetype:thermal:hive_extractor>.removeMapping(output as IIngredient);

<recipetype:thermal:hive_extractor>.removeMapping(<block:minecraft:beehive>);
