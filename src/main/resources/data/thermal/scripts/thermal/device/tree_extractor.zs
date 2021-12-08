/*
    Adds an Arboreal Extractor mapping that will produce 1 mB of Water by extracting a tree with a Chain for the trunk, and Oak Leaves for the leaves.
*/

// <recipetype:thermal:tree_extractor>.addMapping(name as string, trunk as Block, leaves as Block, fluid as IFluidStack);

<recipetype:thermal:tree_extractor>.addMapping("tree_extractor_test", <block:minecraft:chain>, <block:minecraft:oak_leaves>, <fluid:minecraft:water>);

/*
    Removes the Arboreal Extractor mapping that has Oak Log as it's trunk.
    Removes two mappinsg from the Arboreal Extractor.
    
    The first mapping that is removed is the mapping that has an Oak Log as it's trunk.
    The second mapping that is removed is any mappings that output Sap. 
*/

// <recipetype:thermal:tree_extractor>.removeMapping(trunk as MCBlock);
// <recipetype:thermal:tree_extractor>.removeMapping(trunk as CTFluidIngredient);

<recipetype:thermal:tree_extractor>.removeMapping(<block:minecraft:oak_log>);
<recipetype:thermal:tree_extractor>.removeMapping(<fluid:thermal:sap>);