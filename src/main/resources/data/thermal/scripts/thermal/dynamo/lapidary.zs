/*
    Adds White Wool as a Fuel to the Lapidary Dynamo, generating 5000 RF.
*/

// <recipetype:thermal:lapidary_fuel>.addFuel(name as string, ingredient as IIngredient, energy as int);

<recipetype:thermal:lapidary_fuel>.addFuel("custom_lapidary_fuel", <item:minecraft:white_wool>, 5000);

/*
    Removes a Diamond as a fuel in the Lapidary Dynamo.
*/

// <recipetype:thermal:lapidary_fuel>.removeFuel(outputItem as IItemStack);

<recipetype:thermal:lapidary_fuel>.removeFuel(<item:minecraft:diamond>);
