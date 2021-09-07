/*
    Adds two new Fuels to the Disenchantment Dynamo.
    The first Fuel, "custom_disenchantment_fuel", will use White Wool to generate 5000 RF.
    The second Fuel, "mock_disenchantment_fuel", will use Orange Wool to generate 5000 RF.
*/

// <recipetype:thermal:disenchantment_fuel>.addFuel(name as string, ingredient as IIngredient, energy as int);

<recipetype:thermal:disenchantment_fuel>.addFuel("custom_disenchantment_fuel", <item:minecraft:white_wool>, 5000);
<recipetype:thermal:disenchantment_fuel>.addFuel("mock_disenchantment_fuel", <item:minecraft:orange_wool>, 5000);

/*
    Removes Orange Wool as a fuel in the Disenchantment Dynamo.

    This will remove the "mock_disenchantment_fuel" fuel that was added above.
*/

// <recipetype:thermal:disenchantment_fuel>.removeFuel(outputItem as IItemStack);

<recipetype:thermal:disenchantment_fuel>.removeFuel(<item:minecraft:orange_wool>);
