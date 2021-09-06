/*
    Adds two Igneous Extruder mappings.

    The first mapping, "custom_rock_gen_mapping", will generate a Diamond after 5 ticks when Dirt is placed next to an Igneous Extruder.
    The second mapping, "below_custom_rock_gen_mapping", will generate an Iron Ingot after 5 ticks when an Iron Block is placed next to an Igneous Extruder with a Gold Block beneath it.
*/

// <recipetype:thermal:rock_gen>.addMapping(name as string, result as IItemStack, adjacent as Block, below as Block, time as int);

<recipetype:thermal:rock_gen>.addMapping("custom_rock_gen_mapping", <item:minecraft:diamond>, <block:minecraft:dirt>, <block:minecraft:air>, 5);
<recipetype:thermal:rock_gen>.addMapping("below_custom_rock_gen_mapping", <item:minecraft:iron_ingot>, <block:minecraft:gold_block>, <block:minecraft:iron_block>, 5);

/*
    Removes the Igneous Extruder mapping that generates Cobblestone.
*/

// <recipetype:thermal:rock_gen>.removeMapping(output as IIngredient);

<recipetype:thermal:rock_gen>.removeMapping(<item:minecraft:cobblestone>);
